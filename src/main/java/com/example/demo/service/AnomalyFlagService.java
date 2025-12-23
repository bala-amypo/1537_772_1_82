package com.example.demo.service;

import com.example.demo.model.AnomalyFlagRecord;

import java.util.List;

public interface AnomalyFlagService {

    AnomalyFlagRecord flagAnomaly(AnomalyFlagRecord flag);

    AnomalyFlagRecord resolveFlag(Long id);

    List<AnomalyFlagRecord> getFlagsByEmployee(Long employeeId);

    List<AnomalyFlagRecord> getFlagsByMetric(Long metricId);

    List<AnomalyFlagRecord> getAllFlags();
}
