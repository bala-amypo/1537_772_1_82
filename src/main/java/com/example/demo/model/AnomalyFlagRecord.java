package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anomaly_flag_records")
public class AnomalyFlagRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- Relationships ---------- */

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private EmployeeProfile employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "metric_id")
    private ProductivityMetricRecord metric;

    /* ---------- Fields ---------- */

    private String ruleCode;

    private String severity; // LOW, MEDIUM, HIGH

    @Column(columnDefinition = "TEXT")
    private String details;

    private LocalDateTime flaggedAt;

    private Boolean resolved = false;

    /* ---------- Constructors ---------- */

    public AnomalyFlagRecord() {}

    public AnomalyFlagRecord(EmployeeProfile employee,
                             ProductivityMetricRecord metric,
                             String ruleCode,
                             String severity,
                             String details) {
        this.employee = employee;
        this.metric = metric;
        this.ruleCode = ruleCode;
        this.severity = severity;
        this.details = details;
        this.resolved = false;
    }

    @PrePersist
    protected void onFlag() {
        this.flaggedAt = LocalDateTime.now();
        if (this.resolved == null) {
            this.resolved = false;
        }
    }

    /* ---------- Getters & Setters ---------- */

    public Long getId() { return id; }

    public EmployeeProfile getEmployee() { return employee; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }

    public ProductivityMetricRecord getMetric() { return metric; }
    public void setMetric(ProductivityMetricRecord metric) { this.metric = metric; }

    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getFlaggedAt() { return flaggedAt; }

    public Boolean getResolved() { return resolved; }
    public void setResolved(Boolean resolved) { this.resolved = resolved; }
}
