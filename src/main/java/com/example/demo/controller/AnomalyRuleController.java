package com.example.demo.controller;

import com.example.demo.dto.AnomalyRuleDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.AnomalyRule;
import com.example.demo.service.AnomalyRuleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anomaly-rules")
@PreAuthorize("hasAuthority('ADMIN')")
public class AnomalyRuleController {

    private final AnomalyRuleService service;

    public AnomalyRuleController(AnomalyRuleService service) {
        this.service = service;
    }

    @PostMapping
    public AnomalyRuleDto create(@RequestBody AnomalyRuleDto dto) {
        return toDto(service.createRule(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public AnomalyRuleDto update(
            @PathVariable Long id,
            @RequestBody AnomalyRuleDto dto) {
        return toDto(service.updateRule(id, toEntity(dto)));
    }

    @GetMapping("/active")
    public List<AnomalyRuleDto> activeRules() {
        return service.getActiveRules()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AnomalyRuleDto getById(@PathVariable Long id) {
        return service.getAllRules()
                .stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .map(this::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Rule not found"));
    }

    @GetMapping
    public List<AnomalyRuleDto> all() {
        return service.getAllRules()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AnomalyRule toEntity(AnomalyRuleDto dto) {
        AnomalyRule r = new AnomalyRule();
        r.setRuleCode(dto.getRuleCode());
        r.setDescription(dto.getDescription());
        r.setThresholdType(dto.getThresholdType());
        r.setThresholdValue(dto.getThresholdValue());
        r.setActive(dto.getActive());
        return r;
    }

    private AnomalyRuleDto toDto(AnomalyRule r) {
        AnomalyRuleDto dto = new AnomalyRuleDto();
        dto.setId(r.getId());
        dto.setRuleCode(r.getRuleCode());
        dto.setDescription(r.getDescription());
        dto.setThresholdType(r.getThresholdType());
        dto.setThresholdValue(r.getThresholdValue());
        dto.setActive(r.getActive());
        return dto;
    }
}
