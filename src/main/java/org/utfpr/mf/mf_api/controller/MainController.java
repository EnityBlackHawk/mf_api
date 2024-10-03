package org.utfpr.mf.mf_api.controller;


import org.springframework.web.bind.annotation.*;
import org.utfpr.mf.mf_api.dto.BindValueDTO;
import org.utfpr.mf.mf_api.dto.GenerateModelParamDTO;
import org.utfpr.mf.mf_api.dto.MigrateStepParamsDTO;
import org.utfpr.mf.mf_api.service.MigratorService;
import org.utfpr.mf.migration.params.*;
import org.utfpr.mf.model.Credentials;

@RestController
@RequestMapping("/api")
public class MainController {

    private final MigratorService migratorService;

    public MainController(MigratorService migratorService) {
        this.migratorService = migratorService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @PostMapping("/bind-value")
    public boolean bindValue(@RequestBody BindValueDTO bindValueDTO) {
        return migratorService.addBinding(bindValueDTO.getKey(), bindValueDTO.getValue());
    }

    @PostMapping("/acquire-metadata")
    public MetadataInfo acquireMetadata(@RequestBody Credentials credentials) {
        return migratorService.acquireMetadataStep(credentials);
    }

    @PostMapping("/generate-model")
    public Model generateModel(@RequestBody GenerateModelParamDTO dto) {
         return migratorService.generateModelStep(dto.getMetadataInfo(), dto.getSpec());
    }

    @PostMapping("/generate-java-code")
    public GeneratedJavaCode generateJavaCode(@RequestBody Model model) {
        return migratorService.generateJavaCodeStep(model);
    }

    @PostMapping("/migrate-database")
    public VerificationReport migrateDatabaseStep(@RequestBody MigrateStepParamsDTO dto) {
        var m_result = migratorService.migrateDatabaseStep(dto.getGeneratedJavaCode(), dto.getMongoConnectionCredentials(), dto.getCredentials());
        return migratorService.verificationStep(m_result);
    }

    @PostMapping("/acquire-metadata-and-generate-model")
    public Model acquireMetadataAndGenerateModel(@RequestBody Credentials credentials, @RequestBody MigrationSpec spec) {
        return migratorService.acquireMetadataAndGenerateModelStep(credentials, spec);
    }

}
