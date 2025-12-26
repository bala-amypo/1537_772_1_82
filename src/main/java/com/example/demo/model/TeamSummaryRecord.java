package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "team_summaries")
public class TeamSummaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;
    private LocalDate summaryDate;

    private double avgHoursLogged;     // ✅ primitive
    private double avgTasksCompleted;  // ✅ primitive
    private double avgScore;           // ✅ primitive
    private int anomalyCount;           // ✅ primitive

    private LocalDateTime generatedAt;

    // ---------------- GETTERS / SETTERS ----------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public LocalDate getSummaryDate() { return summaryDate; }
    public void setSummaryDate(LocalDate summaryDate) { this.summaryDate = summaryDate; }

    public double getAvgHoursLogged() { return avgHoursLogged; }
    public void setAvgHoursLogged(double avgHoursLogged) { this.avgHoursLogged = avgHoursLogged; }

    public double getAvgTasksCompleted() { return avgTasksCompleted; }
    public void setAvgTasksCompleted(double avgTasksCompleted) { this.avgTasksCompleted = avgTasksCompleted; }

    public double getAvgScore() { return avgScore; }
    public void setAvgScore(double avgScore) { this.avgScore = avgScore; }

    public int getAnomalyCount() { return anomalyCount; }
    public void setAnomalyCount(int anomalyCount) { this.anomalyCount = anomalyCount; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
