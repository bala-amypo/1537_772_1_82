package com.example.demo.repository;

import com.example.demo.model.ProductivityMetricRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductivityMetricRecordRepository extends JpaRepository<ProductivityMetricRecord, Long> {

    // Corrected: traverse the relationship to EmployeeProfile
    List<ProductivityMetricRecord> findByEmployee_EmployeeId(String employeeId);

    Optional<ProductivityMetricRecord> findByEmployee_EmployeeIdAndDate(String employeeId, LocalDate date);
}
