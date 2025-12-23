package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AnomalyFlagRecord;

public interface AnomalyFlagRecordRepository
        extends JpaRepository<AnomalyFlagRecord, Long> {

    List<AnomalyFlagRecord> findByEmployeeId(Long employeeId);

    List<AnomalyFlagRecord> findByMetricId(Long metricId);
}
