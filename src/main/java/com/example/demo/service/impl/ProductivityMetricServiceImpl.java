package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ProductivityMetricService;
import com.example.demo.util.ProductivityCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductivityMetricServiceImpl implements ProductivityMetricService {

    private final ProductivityMetricRecordRepository metricRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final AnomalyRuleRepository ruleRepo;
    private final AnomalyFlagRecordRepository flagRepo;

    public ProductivityMetricServiceImpl(
            ProductivityMetricRecordRepository metricRepo,
            EmployeeProfileRepository employeeRepo,
            AnomalyRuleRepository ruleRepo,
            AnomalyFlagRecordRepository flagRepo) {

        this.metricRepo = metricRepo;
        this.employeeRepo = employeeRepo;
        this.ruleRepo = ruleRepo;
        this.flagRepo = flagRepo;
    }

    @Override
    public ProductivityMetricRecord recordMetric(ProductivityMetricRecord metric) {

        Long employeeId = metric.getEmployee().getId();

        EmployeeProfile employee = employeeRepo.findById(employeeId)
                .filter(EmployeeProfile::getActive)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        metricRepo.findByEmployeeIdAndDate(employeeId, metric.getDate())
                .ifPresent(m -> {
                    throw new IllegalStateException("Metric already exists for this employee and date");
                });

        double score = ProductivityCalculator.computeScore(
                metric.getHoursLogged(),
                metric.getTasksCompleted(),
                metric.getMeetingsAttended()
        );

        metric.setEmployee(employee);
        metric.setProductivityScore(score);

        ProductivityMetricRecord saved = metricRepo.save(metric);

        // anomaly detection (rules applied later in detail)
        ruleRepo.findByActiveTrue().forEach(rule -> {
            if (score < rule.getThresholdValue()) {
                AnomalyFlagRecord flag = new AnomalyFlagRecord(
                        employee,
                        saved,
                        rule.getRuleCode(),
                        "LOW",
                        "Productivity score below threshold"
                );
                flagRepo.save(flag);
            }
        });

        return saved;
    }

    @Override
    public ProductivityMetricRecord updateMetric(Long id, ProductivityMetricRecord updated) {

        ProductivityMetricRecord existing = metricRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Metric not found"));

        existing.setHoursLogged(updated.getHoursLogged());
        existing.setTasksCompleted(updated.getTasksCompleted());
        existing.setMeetingsAttended(updated.getMeetingsAttended());
        existing.setRawDataJson(updated.getRawDataJson());

        double score = ProductivityCalculator.computeScore(
                existing.getHoursLogged(),
                existing.getTasksCompleted(),
                existing.getMeetingsAttended()
        );

        existing.setProductivityScore(score);

        return metricRepo.save(existing);
    }

    @Override
    public List<ProductivityMetricRecord> getMetricsByEmployee(Long employeeId) {
        return metricRepo.findByEmployeeId(employeeId);
    }

    @Override
    public Optional<ProductivityMetricRecord> getMetricById(Long id) {
        return metricRepo.findById(id);
    }

    @Override
    public List<ProductivityMetricRecord> getAllMetrics() {
        return metricRepo.findAll();
    }
}
