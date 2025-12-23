package com.example.demo.service.impl;

import com.example.demo.dto.CredentialStatusDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Credential;
import com.example.demo.model.CredentialVerificationEvent;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.repository.CredentialRepository;
import com.example.demo.repository.CredentialVerificationEventRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.service.CredentialVerificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CredentialVerificationServiceImpl
        implements CredentialVerificationService {

    private final CredentialRepository credentialRepo;
    private final CredentialVerificationEventRepository eventRepo;
    private final EmployeeProfileRepository employeeRepo;

    public CredentialVerificationServiceImpl(
            CredentialRepository credentialRepo,
            CredentialVerificationEventRepository eventRepo,
            EmployeeProfileRepository employeeRepo) {
        this.credentialRepo = credentialRepo;
        this.eventRepo = eventRepo;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Credential registerCredential(Credential credential) {

        EmployeeProfile employee = employeeRepo
                .findById(credential.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (!employee.getActive()) {
            throw new ResourceNotFoundException("Employee not found");
        }

        credentialRepo.findByCredentialId(credential.getCredentialId())
                .ifPresent(c -> {
                    throw new IllegalStateException("Credential mapping exists");
                });

        return credentialRepo.save(credential);
    }

    @Override
    public CredentialStatusDto verifyCredential(String credentialId) {

        Credential credential = credentialRepo
                .findByCredentialId(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential not found"));

        if ("REVOKED".equals(credential.getStatus())) {
            throw new IllegalStateException("Credential revoked");
        }

        if (credential.getExpiresAt() != null &&
                credential.getExpiresAt().isBefore(LocalDateTime.now())) {
            credential.setStatus("EXPIRED");
            credentialRepo.save(credential);
            throw new IllegalStateException("Credential expired");
        }

        credential.setStatus("VERIFIED");
        credentialRepo.save(credential);

        eventRepo.save(new CredentialVerificationEvent(
                credential.getId(),
                "SUCCESS",
                "Credential verified successfully"
        ));

        return new CredentialStatusDto(
                credential.getCredentialId(),
                credential.getStatus(),
                "Verification successful"
        );
    }

    @Override
    public List<Credential> getCredentialsForEmployee(Long employeeId) {
        return credentialRepo.findByEmployeeId(employeeId);
    }

    @Override
    public Credential getCredentialByExternalId(String credentialId) {
        return credentialRepo.findByCredentialId(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential not found"));
    }
}
