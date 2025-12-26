package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String externalId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String status;

    /* ---------- Relationship to EmployeeProfile ---------- */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;

    /* ---------- Constructors ---------- */
    public Credential() {}

    public Credential(String externalId, String type, String status, EmployeeProfile employee) {
        this.externalId = externalId;
        this.type = type;
        this.status = status;
        this.employee = employee;
    }

    /* ---------- Getters & Setters ---------- */
    public Long getId() { return id; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public EmployeeProfile getEmployee() { return employee; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }
}
