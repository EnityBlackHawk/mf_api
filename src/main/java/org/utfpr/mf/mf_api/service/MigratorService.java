package org.utfpr.mf.mf_api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.utfpr.mf.MockLayer;
import org.utfpr.mf.enums.DefaultInjectParams;
import org.utfpr.mf.metadata.DbMetadata;
import org.utfpr.mf.migration.IMfBinder;
import org.utfpr.mf.migration.IMfMigrationStep;
import org.utfpr.mf.migration.MfMigrationStepFactory;
import org.utfpr.mf.migration.MfMigrator;
import org.utfpr.mf.migration.params.*;
import org.utfpr.mf.model.Credentials;
import org.utfpr.mf.mongoConnection.MongoConnectionCredentials;

import java.sql.SQLException;
import java.util.List;

@Service
public class MigratorService {

    private IMfBinder binder = new MfMigrator.Binder();

    public MigratorService() {
        binder.bind(DefaultInjectParams.LLM_KEY.getValue(), System.getenv("LLM_KEY"));
    }

    public boolean addBinding(String key, Object value) {
        binder.bind(key, value);
        return true;
    }

    public MetadataInfo acquireMetadataStep(Credentials credentials) {
        MockLayer.isActivated = false;
        IMfMigrationStep step = factory().createAcquireMetadataStep();
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (MetadataInfo) migrator.execute(credentials);
    }

    public Model generateModelStep(MetadataInfo metadataInfo, MigrationSpec spec) {
        MockLayer.isActivated = false;
        IMfMigrationStep step = factory().createGenerateModelStep(spec);
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (Model) migrator.execute(metadataInfo);
    }

    public GeneratedJavaCode generateJavaCodeStep(Model model) {
        MockLayer.isActivated = false;
        IMfMigrationStep step = factory().createGenerateJavaCodeStep();
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (GeneratedJavaCode) migrator.execute(model);
    }

    public MigrationDatabaseReport migrateDatabaseStep(GeneratedJavaCode generatedJavaCode, MongoConnectionCredentials connectionCredentials, @Nullable Credentials credentials) {

        if(!binder.has(DefaultInjectParams.DB_METADATA.getValue())) {
            if(credentials == null) {
                throw new RuntimeException("DB Metadata is not set and no credentials were provided");
            }
            try {
                binder.bind(DefaultInjectParams.DB_METADATA.getValue(),
                        new DbMetadata(credentials, null));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        MockLayer.isActivated = true;
        IMfMigrationStep step = factory().createMigrateDatabaseStep(connectionCredentials);
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (MigrationDatabaseReport) migrator.execute(generatedJavaCode);
    }

    public VerificationReport verificationStep(MigrationDatabaseReport report) {
        MockLayer.isActivated = true;
        IMfMigrationStep step = factory().createValidatorStep();
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (VerificationReport) migrator.execute(report);
    }

    public Model acquireMetadataAndGenerateModelStep(Credentials credentials, MigrationSpec spec) {
        MockLayer.isActivated = true;
        IMfMigrationStep acquireMetadataStep = factory().createAcquireMetadataStep();
        IMfMigrationStep generateModelStep = factory().createGenerateModelStep(spec);
        MfMigrator migrator = new MfMigrator(binder, List.of(acquireMetadataStep, generateModelStep));
        return (Model) migrator.execute(credentials);
    }



    private MfMigrationStepFactory factory() {
        return new MfMigrationStepFactory();
    }

}
