package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "anomaly_flag_records")
public class AnomalyFlagRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- Relationship to ProductivityMetricRecord ---------- */
    @ManyToOne
    @JoinColumn(name = "metric_id", nullable = false)
    private ProductivityMetricRecord metric;

    /* ---------- Relationship to EmployeeProfile ---------- */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;

    /* ---------- Constructors ---------- */
    public AnomalyFlagRecord() {}

    public AnomalyFlagRecord(ProductivityMetricRecord metric, EmployeeProfile employee) {
        this.metric = metric;
        this.employee = employee;
    }

    /* ---------- Getters & Setters ---------- */
    public Long getId() { return id; }

    public ProductivityMetricRecord getMetric() { return metric; }
    public void setMetric(ProductivityMetricRecord metric) { this.metric = metric; }

    public EmployeeProfile getEmployee() { return employee; }
    public void setEmployee(EmployeeProfile employee) { this.employee = employee; }
}
