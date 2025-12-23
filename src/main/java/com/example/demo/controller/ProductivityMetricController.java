package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ProductivityMetricRecord;
import com.example.demo.service.ProductivityMetricService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class ProductivityMetricController {

    private final ProductivityMetricService service;

    public ProductivityMetricController(ProductivityMetricService service) {
        this.service = service;
    }

    /* 1. Record metric */
    @PostMapping
    public ProductivityMetricRecord record(@RequestBody ProductivityMetricRecord metric) {
        return service.recordMetric(metric);
    }

    /* 2. Update metric */
    @PutMapping("/{id}")
    public ProductivityMetricRecord update(
            @PathVariable Long id,
            @RequestBody ProductivityMetricRecord metric) {
        return service.updateMetric(id, metric);
    }

    /* 3. Metrics by employee */
    @GetMapping("/employee/{employeeId}")
    public List<ProductivityMetricRecord> byEmployee(@PathVariable Long employeeId) {
        return service.getMetricsByEmployee(employeeId);
    }

    /* 4. Single metric */
    @GetMapping("/{id}")
    public ProductivityMetricRecord getOne(@PathVariable Long id) {
        return service.getMetricById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Metric not found"));
    }

    /* 5. All metrics */
    @GetMapping
    public List<ProductivityMetricRecord> all() {
        return service.getAllMetrics();
    }
}
