package com.example.demo.service.impl;

import com.example.demo.model.ProductivityMetricRecord;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.model.AnomalyFlagRecord;
import com.example.demo.repository.ProductivityMetricRecordRepository;
import com.example.demo.repository.AnomalyFlagRecordRepository;
import com.example.demo.service.ProductivityMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductivityMetricServiceImpl implements ProductivityMetricService {

    private final ProductivityMetricRecordRepository metricRepo;
    private final AnomalyFlagRecordRepository anomalyFlagRepository;

    @Autowired
    public ProductivityMetricServiceImpl(ProductivityMetricRecordRepository metricRepo,
                                         AnomalyFlagRecordRepository anomalyFlagRepository) {
        this.metricRepo = metricRepo;
        this.anomalyFlagRepository = anomalyFlagRepository;
    }

    @Override
    public List<ProductivityMetricRecord> getMetricsByEmployee(Long employeeId) {
        return metricRepo.findByEmployeeId(employeeId);
    }

    @Override
    public Optional<ProductivityMetricRecord> getMetricByEmployeeAndDate(Long employeeId, LocalDate date) {
        return metricRepo.findByEmployeeIdAndDate(employeeId, date);
    }

    @Override
    public Optional<ProductivityMetricRecord> getMetricById(Long metricId) {
        return metricRepo.findById(metricId);
    }

    @Override
    public void createAnomalyFlag(EmployeeProfile employeeProfile, ProductivityMetricRecord metricRecord) {
        AnomalyFlagRecord flag = new AnomalyFlagRecord(
                employeeProfile,
                metricRecord,
                "RULE_CODE_1",
                "HIGH",
                "Detected anomaly details"
        );
        anomalyFlagRepository.save(flag);
    }

    @Override
    public ProductivityMetricRecord saveMetric(ProductivityMetricRecord metricRecord) {
        return metricRepo.save(metricRecord);
    }

    @Override
    public List<ProductivityMetricRecord> getAllMetrics() {
        return metricRepo.findAll();
    }

    @Override
    public ProductivityMetricRecord updateMetric(Long metricId, ProductivityMetricRecord metric) {
        return metricRepo.findById(metricId)
                .map(existing -> {
                    existing.setValue(metric.getValue()); // update fields
                    existing.setDate(metric.getDate());
                    return metricRepo.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Metric not found"));
    }
}
