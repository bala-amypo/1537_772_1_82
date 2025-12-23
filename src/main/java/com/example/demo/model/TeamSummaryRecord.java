package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "team_summary_records",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"teamName", "summaryDate"})
    }
)
public class TeamSummaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    private LocalDate summaryDate;

    private Double avgHoursLogged;

    private Double avgTasksCompleted;

    private Double avgScore;

    private Integer anomalyCount;

    private LocalDateTime generatedAt;

    /* ---------- Constructors ---------- */

    public TeamSummaryRecord() {}

    public TeamSummaryRecord(String teamName,
                             LocalDate summaryDate,
                             Double avgHoursLogged,
                             Double avgTasksCompleted,
                             Double avgScore,
                             Integer anomalyCount) {
        this.teamName = teamName;
        this.summaryDate = summaryDate;
        this.avgHoursLogged = avgHoursLogged;
        this.avgTasksCompleted = avgTasksCompleted;
        this.avgScore = avgScore;
        this.anomalyCount = anomalyCount;
    }

    @PrePersist
    protected void onGenerate() {
        this.generatedAt = LocalDateTime.now();
    }

    /* ---------- Getters & Setters ---------- */

    public Long getId() { return id; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public LocalDate getSummaryDate() { return summaryDate; }
    public void setSummaryDate(LocalDate summaryDate) { this.summaryDate = summaryDate; }

    public Double getAvgHoursLogged() { return avgHoursLogged; }
    public void setAvgHoursLogged(Double avgHoursLogged) { this.avgHoursLogged = avgHoursLogged; }

    public Double getAvgTasksCompleted() { return avgTasksCompleted; }
    public void setAvgTasksCompleted(Double avgTasksCompleted) { this.avgTasksCompleted = avgTasksCompleted; }

    public Double getAvgScore() { return avgScore; }
    public void setAvgScore(Double avgScore) { this.avgScore = avgScore; }

    public Integer getAnomalyCount() { return anomalyCount; }
    public void setAnomalyCount(Integer anomalyCount) { this.anomalyCount = anomalyCount; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
}
