package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "team_summary_records")
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

    public Long getId() { return id; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public LocalDate getSummaryDate() { return summaryDate; }
    public void setSummaryDate(LocalDate summaryDate) { this.summaryDate = summaryDate; }

    public Double getAvgHoursLogged() {
        return avgHoursLogged == null ? 0.0 : avgHoursLogged;
    }
    public void setAvgHoursLogged(Double avgHoursLogged) {
        this.avgHoursLogged = avgHoursLogged;
    }

    public Double getAvgTasksCompleted() {
        return avgTasksCompleted == null ? 0.0 : avgTasksCompleted;
    }
    public void setAvgTasksCompleted(Double avgTasksCompleted) {
        this.avgTasksCompleted = avgTasksCompleted;
    }

    public Double getAvgScore() {
        return avgScore == null ? 0.0 : avgScore;
    }
    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Integer getAnomalyCount() {
        return anomalyCount == null ? 0 : anomalyCount;
    }
    public void setAnomalyCount(Integer anomalyCount) {
        this.anomalyCount = anomalyCount;
    }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}
