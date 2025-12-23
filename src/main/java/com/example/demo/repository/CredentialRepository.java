package com.example.demo.repository;

import com.example.demo.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    // Corrected: traversing the relationship to EmployeeProfile
    List<Credential> findByEmployee_EmployeeId(String employeeId);

    // Optional: find by type or status
    List<Credential> findByType(String type);
    List<Credential> findByStatus(String status);
}
