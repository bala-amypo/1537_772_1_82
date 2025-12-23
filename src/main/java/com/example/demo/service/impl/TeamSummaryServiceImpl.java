package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.ProductivityMetricRecord;
import com.example.demo.model.TeamSummaryRecord;
import com.example.demo.repository.AnomalyFlagRecordRepository;
import com.example.demo.repository.EmployeeProfileRepository;
import com.example.demo.repository.ProductivityMetricRecordRepository;
import com.example.demo.repository.TeamSummaryRecordRepository;
import com.example.demo.service.TeamSummaryService;

@Service
@Transactional
public class TeamSummaryServiceImpl implements TeamSummaryService {

    private final TeamSummaryRecordRepository summaryRepo;
    private final ProductivityMetricRecordRepository metricRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final AnomalyFlagRecordRepository flagRepo;

    public TeamSummaryServiceImpl(
            TeamSummaryRecordRepository summaryRepo,
            ProductivityMetricRecordRepository metricRepo,
            EmployeeProfileRepository employeeRepo,
            AnomalyFlagRecordRepository flagRepo) {

        this.summaryRepo = summaryRepo;
        this.metricRepo = metricRepo;
        this.employeeRepo = employeeRepo;
        this.flagRepo = flagRepo;
    }

    @Override
    public TeamSummaryRecord generateSummary(String teamName, LocalDate summaryDate) {

        List<EmployeeProfile> employees =
                employeeRepo.findByTeamName(teamName);

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Team not found");
        }

        double totalHours = 0;
        double totalTasks = 0;
        double totalScore = 0;
        int metricCount = 0;
        int anomalyCount = 0;

        for (EmployeeProfile emp : employees) {

            List<ProductivityMetricRecord> metrics =
                    metricRepo.findByEmployeeId(emp.getId());

            for (ProductivityMetricRecord m : metrics) {

                if (!m.getDate().equals(summaryDate)) {
                    continue;
                }

                totalHours += m.getHoursLogged();
                totalTasks += m.getTasksCompleted();
                totalScore += m.getProductivityScore();
                metricCount++;

                anomalyCount += flagRepo.findByMetricId(m.getId()).size();
            }
        }

        if (metricCount == 0) {
            throw new ResourceNotFoundException("No metrics found");
        }

        TeamSummaryRecord summary = new TeamSummaryRecord();
        summary.setTeamName(teamName);
        summary.setSummaryDate(summaryDate);
        summary.setAvgHoursLogged(totalHours / metricCount);
        summary.setAvgTasksCompleted(totalTasks / metricCount);
        summary.setAvgScore(totalScore / metricCount);
        summary.setAnomalyCount(anomalyCount);
        summary.setGeneratedAt(LocalDateTime.now());

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
