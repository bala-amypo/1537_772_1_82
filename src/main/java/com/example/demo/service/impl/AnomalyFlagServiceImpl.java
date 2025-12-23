package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AnomalyFlagRecord;
import com.example.demo.repository.AnomalyFlagRecordRepository;
import com.example.demo.service.AnomalyFlagService;

@Service
@Transactional
public class AnomalyFlagServiceImpl implements AnomalyFlagService {

    private final AnomalyFlagRecordRepository flagRepository;

    public AnomalyFlagServiceImpl(AnomalyFlagRecordRepository flagRepository) {
        this.flagRepository = flagRepository;
    }

    @Override
    public AnomalyFlagRecord flagAnomaly(AnomalyFlagRecord flag) {
        flag.setResolved(false);
        flag.setFlaggedAt(LocalDateTime.now());
        return flagRepository.save(flag);
    }

    @Override
    public AnomalyFlagRecord resolveFlag(Long id) {
        AnomalyFlagRecord flag = flagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anomaly flag not found"));

        flag.setResolved(true);
        return flagRepository.save(flag);
    }

    @Override
    public List<AnomalyFlagRecord> getFlagsByEmployee(Long employeeId) {
        return flagRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<AnomalyFlagRecord> getFlagsByMetric(Long metricId) {
        return flagRepository.findByMetricId(metricId);
    }

    @Override
    public List<AnomalyFlagRecord> getAllFlags() {
        return flagRepository.findAll();
    }
}
