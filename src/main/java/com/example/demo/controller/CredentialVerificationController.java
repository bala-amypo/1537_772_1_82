package com.example.demo.controller;

import com.example.demo.dto.CredentialStatusDto;
import com.example.demo.model.Credential;
import com.example.demo.service.CredentialVerificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credentials")
public class CredentialVerificationController {

    private final CredentialVerificationService service;

    public CredentialVerificationController(
            CredentialVerificationService service) {
        this.service = service;
    }

    /* 1. Register credential */
    @PostMapping
    public Credential register(@RequestBody Credential credential) {
        return service.registerCredential(credential);
    }

    /* 2. Verify credential */
    @PostMapping("/{credentialId}/verify")
    public CredentialStatusDto verify(
            @PathVariable String credentialId) {
        return service.verifyCredential(credentialId);
    }

    /* 3. Credentials for employee */
    @GetMapping("/employee/{employeeId}")
    public List<Credential> byEmployee(
            @PathVariable Long employeeId) {
        return service.getCredentialsForEmployee(employeeId);
    }

    /* 4. Get credential by external id */
    @GetMapping("/{credentialId}")
    public Credential getById(
            @PathVariable String credentialId) {
        return service.getCredentialByExternalId(credentialId);
    }
}
