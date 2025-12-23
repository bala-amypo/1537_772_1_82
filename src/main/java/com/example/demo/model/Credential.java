package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "credentials",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "credentialId")
    }
)
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Reference to EmployeeProfile */
    private Long employeeId;

    @Column(nullable = false, unique = true)
    private String credentialId;

    private String issuer;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    private String status; // PENDING, VERIFIED, REVOKED, EXPIRED

    @Lob
    private String metadataJson;

    /* ---------- Constructors ---------- */

    public Credential() {}

    public Credential(Long employeeId,
                      String credentialId,
                      String issuer,
                      LocalDateTime expiresAt,
                      String status,
                      String metadataJson) {
        this.employeeId = employeeId;
        this.credentialId = credentialId;
        this.issuer = issuer;
        this.expiresAt = expiresAt;
        this.status = status;
        this.metadataJson = metadataJson;
    }

    @PrePersist
    protected void onCreate() {
        this.issuedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "PENDING";
        }
    }

    /* ---------- Getters & Setters ---------- */

    public Long getId() { return id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getCredentialId() { return credentialId; }
    public void setCredentialId(String credentialId) { this.credentialId = credentialId; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    public LocalDateTime getIssuedAt() { return issuedAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMetadataJson() { return metadataJson; }
    public void setMetadataJson(String metadataJson) { this.metadataJson = metadataJson; }
}
