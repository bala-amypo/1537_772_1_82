package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "productivity_metric_records")
public class ProductivityMetricRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;

    @Column(nullable = false)
    private LocalDate date;

    private int hoursLogged;
    private int tasksCompleted;
    private int meetingsAttended;

    private double productivityScore;

    @Lob
    private String rawDataJson;

    @ElementCollection
    @CollectionTable(name = "productivity_anomaly_flags")
    @MapKeyColumn(name = "flag")
    @Column(name = "value")
    private Map<String, Boolean> anomalyFlags;

    // ---------- getters & setters ----------

    public Long getId() {
        return id;
    }

    public EmployeeProfile getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeProfile employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHoursLogged() {
        return hoursLogged;
    }

    public void setHoursLogged(int hoursLogged) {
        this.hoursLogged = hoursLogged;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(int tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public int getMeetingsAttended() {
        return meetingsAttended;
    }

    public void setMeetingsAttended(int meetingsAttended) {
        this.meetingsAttended = meetingsAttended;
    }

    public double getProductivityScore() {
        return productivityScore;
    }

    public void setProductivityScore(double productivityScore) {
        this.productivityScore = productivityScore;
    }

    public String getRawDataJson() {
        return rawDataJson;
    }

    public void setRawDataJson(String rawDataJson) {
        this.rawDataJson = rawDataJson;
    }

    public Map<String, Boolean> getAnomalyFlags() {
        return anomalyFlags;
    }

    public void setAnomalyFlags(Map<String, Boolean> anomalyFlags) {
        this.anomalyFlags = anomalyFlags;
    }
}
