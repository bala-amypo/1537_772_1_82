package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anomaly_flags")
public class AnomalyFlagRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private Long metricId;

    private String ruleCode;

    private String severity;

    private String details;

    private LocalDateTime flaggedAt;

    @Column(nullable = false)
    private Boolean resolved = false;

    /* ---------- Constructors ---------- */
    public AnomalyFlagRecord() {}

    public AnomalyFlagRecord(ProductivityMetricRecord metric, EmployeeProfile employee) {
        this.metricId = metric.getId();
        this.employeeId = employee.getId();
        this.flaggedAt = LocalDateTime.now();
        this.resolved = false;
    }

    public AnomalyFlagRecord(EmployeeProfile employee, ProductivityMetricRecord metric,
                             String ruleCode, String severity, String details) {
        this.employeeId = employee.getId();
        this.metricId = metric.getId();
        this.ruleCode = ruleCode;
        this.severity = severity;
        this.details = details;
        this.flaggedAt = LocalDateTime.now();
        this.resolved = false;
    }

    /* ---------- Getters & Setters ---------- */
    public Long getId() { return id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Long getMetricId() { return metricId; }
    public void setMetricId(Long metricId) { this.metricId = metricId; }

    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getFlaggedAt() { return flaggedAt; }
    public void setFlaggedAt(LocalDateTime flaggedAt) { this.flaggedAt = flaggedAt; }

    public Boolean getResolved() { return resolved; }
    public void setResolved(Boolean resolved) { this.resolved = resolved; }
}
