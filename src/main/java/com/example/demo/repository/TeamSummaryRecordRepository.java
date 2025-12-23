package com.example.demo.repository;

import com.example.demo.model.TeamSummaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeamSummaryRecordRepository
        extends JpaRepository<TeamSummaryRecord, Long> {

    List<TeamSummaryRecord> findByTeamName(String teamName);

    Optional<TeamSummaryRecord> findByTeamNameAndSummaryDate(
            String teamName, LocalDate summaryDate);
}
