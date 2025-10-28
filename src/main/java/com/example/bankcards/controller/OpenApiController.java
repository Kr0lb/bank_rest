package com.example.bankcards.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@RestController
public class OpenApiController {

    @Value("${openapi.url}")
    private String port;

    @GetMapping(value = "/openapi.yaml", produces = "application/vnd.oai.openapi")
    public ResponseEntity<String> getOpenApiYaml() throws Exception {
        FileSystemResource resource = new FileSystemResource("docs/openapi.yaml");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String yamlContent = FileCopyUtils.copyToString(reader);
            yamlContent = yamlContent.replace("${openapi.url}", port);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.oai.openapi+yaml"))
                    .body(yamlContent);
        }
    }
}


