package com.example.demo.controller;

import com.example.demo.model.AnomalyFlagRecord;
import com.example.demo.service.AnomalyFlagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
public class AnomalyFlagController {

    private final AnomalyFlagService service;

    public AnomalyFlagController(AnomalyFlagService service) {
        this.service = service;
    }

    @PostMapping
    public AnomalyFlagRecord create(@RequestBody AnomalyFlagRecord flag) {
        return service.flagAnomaly(flag);
    }

    @PutMapping("/{id}/resolve")
    public AnomalyFlagRecord resolve(@PathVariable Long id) {
        return service.resolveFlag(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<AnomalyFlagRecord> byEmployee(@PathVariable Long employeeId) {
        return service.getFlagsByEmployee(employeeId);
    }

    @GetMapping("/metric/{metricId}")
    public List<AnomalyFlagRecord> byMetric(@PathVariable Long metricId) {
        return service.getFlagsByMetric(metricId);
    }

    @GetMapping
    public List<AnomalyFlagRecord> all() {
        return service.getAllFlags();
    }
}
