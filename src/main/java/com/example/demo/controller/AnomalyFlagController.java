package com.example.demo.controller;

import com.example.demo.dto.AnomalyFlagDto;
import com.example.demo.model.AnomalyFlagRecord;
import com.example.demo.service.AnomalyFlagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anomalies")
@PreAuthorize("hasAuthority('ADMIN')")
public class AnomalyFlagController {

    private final AnomalyFlagService service;

    public AnomalyFlagController(AnomalyFlagService service) {
        this.service = service;
    }

    @PostMapping
    public AnomalyFlagDto create(@RequestBody AnomalyFlagDto dto) {
        return toDto(service.flagAnomaly(toEntity(dto)));
    }

    @PutMapping("/{id}/resolve")
    public AnomalyFlagDto resolve(@PathVariable Long id) {
        return toDto(service.resolveFlag(id));
    }

    @GetMapping("/employee/{employeeId}")
    public List<AnomalyFlagDto> byEmployee(@PathVariable Long employeeId) {
        return service.getFlagsByEmployee(employeeId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/metric/{metricId}")
    public List<AnomalyFlagDto> byMetric(@PathVariable Long metricId) {
        return service.getFlagsByMetric(metricId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<AnomalyFlagDto> all() {
        return service.getAllFlags()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AnomalyFlagRecord toEntity(AnomalyFlagDto dto) {
        AnomalyFlagRecord r = new AnomalyFlagRecord();
        r.setEmployeeId(dto.getEmployeeId());
        r.setMetricId(dto.getMetricId());
        r.setRuleCode(dto.getRuleCode());
        r.setSeverity(dto.getSeverity());
        r.setDetails(dto.getDetails());
        return r;
    }

    private AnomalyFlagDto toDto(AnomalyFlagRecord r) {
        AnomalyFlagDto dto = new AnomalyFlagDto();
        dto.setId(r.getId());
        dto.setEmployeeId(r.getEmployeeId());
        dto.setMetricId(r.getMetricId());
        dto.setRuleCode(r.getRuleCode());
        dto.setSeverity(r.getSeverity());
        dto.setDetails(r.getDetails());
        dto.setFlaggedAt(r.getFlaggedAt());
        dto.setResolved(r.getResolved());
        return dto;
    }
}
