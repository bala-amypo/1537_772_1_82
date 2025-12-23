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
    public void createAnomalyFlag(EmployeeProfile employeeProfile, ProductivityMetricRecord metricRecord) {
        // Create anomaly flag with correct constructor
        AnomalyFlagRecord flag = new AnomalyFlagRecord(
                employeeProfile,
                metricRecord,
                "RULE_CODE_1",   // Example rule code
                "HIGH",          // Severity
                "Detected anomaly details" // Description
        );
        anomalyFlagRepository.save(flag);
    }

    @Override
    public void saveMetric(ProductivityMetricRecord metricRecord) {
        metricRepo.save(metricRecord);
    }
}
