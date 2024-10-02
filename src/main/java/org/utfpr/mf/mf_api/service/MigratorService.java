package org.utfpr.mf.mf_api.service;

import org.springframework.stereotype.Service;
import org.utfpr.mf.MockLayer;
import org.utfpr.mf.enums.DefaultInjectParams;
import org.utfpr.mf.migration.IMfBinder;
import org.utfpr.mf.migration.IMfMigrationStep;
import org.utfpr.mf.migration.MfMigrationStepFactory;
import org.utfpr.mf.migration.MfMigrator;
import org.utfpr.mf.migration.params.*;
import org.utfpr.mf.model.Credentials;
import org.utfpr.mf.mongoConnection.MongoConnectionCredentials;

import java.util.List;

@Service
public class MigratorService {

    private IMfBinder binder = new MfMigrator.Binder();

    public MigratorService() {
        binder.bind(DefaultInjectParams.LLM_KEY.getValue(), System.getenv("LLM_KEY"));
    }

    public MetadataInfo acquireMetadataStep(Credentials credentials) {
        MockLayer.isActivated = true;
        IMfMigrationStep step = factory().createAcquireMetadataStep();
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (MetadataInfo) migrator.execute(credentials);
    }

    public Model generateModelStep(MetadataInfo metadataInfo, MigrationSpec spec) {
        MockLayer.isActivated = true;
        IMfMigrationStep step = factory().createGenerateModelStep(spec);
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (Model) migrator.execute(metadataInfo);
    }

    public GeneratedJavaCode generateJavaCodeStep(Model model) {
        MockLayer.isActivated = true;
        IMfMigrationStep step = factory().createGenerateJavaCodeStep();
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (GeneratedJavaCode) migrator.execute(model);
    }

    public MigrationDatabaseReport migrateDatabaseStep(GeneratedJavaCode generatedJavaCode, MongoConnectionCredentials connectionCredentials) {
        MockLayer.isActivated = true;
        IMfMigrationStep step = factory().createMigrateDatabaseStep(connectionCredentials);
        MfMigrator migrator = new MfMigrator(binder, List.of(step));
        return (MigrationDatabaseReport) migrator.execute(generatedJavaCode);
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
