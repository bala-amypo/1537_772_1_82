package com.example.demo.repository;

import com.example.demo.model.AnomalyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnomalyRuleRepository
        extends JpaRepository<AnomalyRule, Long> {

    List<AnomalyRule> findByActiveTrue();

    Optional<AnomalyRule> findByRuleCode(String ruleCode);
}
