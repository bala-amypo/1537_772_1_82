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

    /* 1. Create anomaly flag */
    @PostMapping
    public AnomalyFlagRecord create(@RequestBody AnomalyFlagRecord flag) {
        return service.flagAnomaly(flag);
    }

    /* 2. Resolve anomaly */
    @PutMapping("/{id}/resolve")
    public AnomalyFlagRecord resolve(@PathVariable Long id) {
        return service.resolveFlag(id);
    }

    /* 3. Flags by employee */
    @GetMapping("/employee/{employeeId}")
    public List<AnomalyFlagRecord> byEmployee(@PathVariable Long employeeId) {
        return service.getFlagsByEmployee(employeeId);
    }

    /* 4. Flags by metric */
    @GetMapping("/metric/{metricId}")
    public List<AnomalyFlagRecord> byMetric(@PathVariable Long metricId) {
        return service.getFlagsByMetric(metricId);
    }

    /* 5. All flags */
    @GetMapping
    public List<AnomalyFlagRecord> all() {
        return service.getAllFlags();
    }
}
