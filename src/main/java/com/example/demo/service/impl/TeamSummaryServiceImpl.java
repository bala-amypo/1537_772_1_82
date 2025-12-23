package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ProductivityMetricRecord;
import com.example.demo.model.TeamSummaryRecord;
import com.example.demo.repository.ProductivityMetricRecordRepository;
import com.example.demo.repository.TeamSummaryRecordRepository;
import com.example.demo.service.TeamSummaryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TeamSummaryServiceImpl implements TeamSummaryService {

    private final TeamSummaryRecordRepository summaryRepo;
    private final ProductivityMetricRecordRepository metricRepo;

    public TeamSummaryServiceImpl(
            TeamSummaryRecordRepository summaryRepo,
            ProductivityMetricRecordRepository metricRepo) {
        this.summaryRepo = summaryRepo;
        this.metricRepo = metricRepo;
    }

    @Override
    public TeamSummaryRecord generateSummary(String teamName, LocalDate summaryDate) {

        summaryRepo.findByTeamNameAndSummaryDate(teamName, summaryDate)
                .ifPresent(s -> {
                    throw new IllegalStateException(
                            "Summary already exists for this team and date"
                    );
                });

        List<ProductivityMetricRecord> metrics =
                metricRepo.findAll().stream()
                        .filter(m -> m.getEmployee() != null)
                        .filter(m -> teamName.equals(m.getEmployee().getTeamName()))
                        .filter(m -> summaryDate.equals(m.getDate()))
                        .toList();

        if (metrics.isEmpty()) {
            throw new ResourceNotFoundException("No productivity data found for summary");
        }

        double avgHours = metrics.stream()
                .mapToDouble(m -> m.getHoursLogged() != null ? m.getHoursLogged() : 0.0)
                .average()
                .orElse(0.0);

        double avgTasks = metrics.stream()
                .mapToInt(m -> m.getTasksCompleted() != null ? m.getTasksCompleted() : 0)
                .average()
                .orElse(0.0);

        double avgScore = metrics.stream()
                .mapToDouble(m -> m.getProductivityScore() != null ? m.getProductivityScore() : 0.0)
                .average()
                .orElse(0.0);

        int anomalyCount = metrics.stream()
                .mapToInt(m ->
                        m.getAnomalyFlags() != null ? m.getAnomalyFlags().size() : 0
                )
                .sum();

        TeamSummaryRecord summary = new TeamSummaryRecord(
                teamName,
                summaryDate,
                avgHours,
                avgTasks,
                avgScore,
                anomalyCount
        );

        return summaryRepo.save(summary);
    }

    @Override
    public List<TeamSummaryRecord> getSummariesByTeam(String teamName) {
        return summaryRepo.findByTeamName(teamName);
    }

    @Override
    public List<TeamSummaryRecord> getAllSummaries() {
        return summaryRepo.findAll();
    }
}
