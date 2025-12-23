package com.example.demo.service.impl;

import com.example.demo.model.AnomalyFlagRecord;
import com.example.demo.repository.AnomalyFlagRecordRepository;
import com.example.demo.service.AnomalyFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnomalyFlagServiceImpl implements AnomalyFlagService {

    private final AnomalyFlagRecordRepository anomalyFlagRepository;

    @Autowired
    public AnomalyFlagServiceImpl(AnomalyFlagRecordRepository anomalyFlagRepository) {
        this.anomalyFlagRepository = anomalyFlagRepository;
    }

    @Override
    public List<AnomalyFlagRecord> getFlagsByEmployee(Long employeeId) {
        return anomalyFlagRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<AnomalyFlagRecord> getFlagsByMetric(Long metricId) {
        return anomalyFlagRepository.findByMetricId(metricId);
    }

    @Override
    public void resolveFlag(AnomalyFlagRecord flag) {
        flag.setResolved(true);
        anomalyFlagRepository.save(flag);
    }

    @Override
    public void saveFlag(AnomalyFlagRecord flag) {
        anomalyFlagRepository.save(flag);
    }
}
