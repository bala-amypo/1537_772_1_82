package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AnomalyRule;
import com.example.demo.service.AnomalyRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomaly-rules")
public class AnomalyRuleController {

    private final AnomalyRuleService service;

    public AnomalyRuleController(AnomalyRuleService service) {
        this.service = service;
    }

    @PostMapping
    public AnomalyRule create(@RequestBody AnomalyRule rule) {
        return service.createRule(rule);
    }

    @PutMapping("/{id}")
    public AnomalyRule update(
            @PathVariable Long id,
            @RequestBody AnomalyRule rule) {
        return service.updateRule(id, rule);
    }

    @GetMapping("/active")
    public List<AnomalyRule> activeRules() {
        return service.getActiveRules();
    }

    @GetMapping("/{id}")
    public AnomalyRule getById(@PathVariable Long id) {
        return service.getAllRules().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @GetMapping
    public List<AnomalyRule> all() {
        return service.getAllRules();
    }
}
