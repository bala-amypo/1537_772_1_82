package com.example.demo.repository;

import com.example.demo.model.CredentialVerificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialVerificationEventRepository
        extends JpaRepository<CredentialVerificationEvent, Long> {

    List<CredentialVerificationEvent> findByCredentialId(Long credentialId);
}
