package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
    name = "productivity_metric_records",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employee_id", "date"})
    }
)
public class ProductivityMetricRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ---------- Relationships ---------- */

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private EmployeeProfile employee;

    @OneToMany(mappedBy = "metric", cascade = CascadeType.ALL)
    private List<AnomalyFlagRecord> anomalyFlags;

    /* ---------- Fields ---------- */

    private LocalDate date;

    private Double hoursLogged;

    private Integer
