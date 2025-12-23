package com.example.demo.controller;

import com.example.demo.service.CredentialVerificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credentials")
public class CredentialVerificationController {

    private final CredentialVerificationService service;

    public CredentialVerificationController(
            CredentialVerificationService service) {
        this.service = service;
    }

    @PostMapping("/verify/{id}")
    public String verify(@PathVariable Long id) {
        return service.verifyCredential(id);
    }
}
