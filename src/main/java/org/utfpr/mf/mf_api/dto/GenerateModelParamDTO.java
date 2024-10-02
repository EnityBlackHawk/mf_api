package org.utfpr.mf.mf_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.utfpr.mf.migration.params.MetadataInfo;
import org.utfpr.mf.migration.params.MigrationSpec;

@Data
@NoArgsConstructor
public class GenerateModelParamDTO {
    MetadataInfo metadataInfo;
    MigrationSpec spec;
}
