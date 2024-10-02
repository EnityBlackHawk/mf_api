package org.utfpr.mf.mf_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.utfpr.mf.mf_api.dto.GenerateModelParamDTO;
import org.utfpr.mf.mf_api.service.MigratorService;
import org.utfpr.mf.migration.params.MetadataInfo;
import org.utfpr.mf.migration.params.MigrationSpec;
import org.utfpr.mf.migration.params.Model;
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

    @PostMapping("/acquire-metadata")
    public MetadataInfo acquireMetadata(@RequestBody Credentials credentials) {
        return migratorService.acquireMetadataStep(credentials);
    }

    @PostMapping("/generate-model")
    public Model generateModel(@RequestBody GenerateModelParamDTO dto) {
         return migratorService.generateModelStep(dto.getMetadataInfo(), dto.getSpec());
    }


    @PostMapping("/acquire-metadata-and-generate-model")
    public Model acquireMetadataAndGenerateModel(@RequestBody Credentials credentials, @RequestBody MigrationSpec spec) {
        return migratorService.acquireMetadataAndGenerateModelStep(credentials, spec);
    }

}
