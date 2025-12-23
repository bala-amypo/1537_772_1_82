package com.example.demo.service.impl;

import com.example.demo.model.CredentialVerificationEvent;
import com.example.demo.repository.CredentialVerificationEventRepository;
import com.example.demo.service.CredentialVerificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CredentialVerificationServiceImpl
        implements CredentialVerificationService {

    private final CredentialVerificationEventRepository repository;

    public CredentialVerificationServiceImpl(
            CredentialVerificationEventRepository repository) {
        this.repository = repository;
    }

    @Override
    public String verifyCredential(Long credentialId) {

        CredentialVerificationEvent event =
                new CredentialVerificationEvent();

        event.setCredentialId(credentialId);
        event.setStatus("VERIFIED");
        event.setRemarks("Verification successful");
        event.setVerifiedAt(LocalDateTime.now());

        repository.save(event);
        return "VERIFIED";
    }
}
