package org.utfpr.mf.mf_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.utfpr.mf.migration.params.GeneratedJavaCode;
import org.utfpr.mf.model.Credentials;
import org.utfpr.mf.mongoConnection.MongoConnectionCredentials;

@Data
@NoArgsConstructor
public class MigrateStepParamsDTO {
    private GeneratedJavaCode generatedJavaCode;
    private MongoConnectionCredentials mongoConnectionCredentials;
    private Credentials credentials;
}