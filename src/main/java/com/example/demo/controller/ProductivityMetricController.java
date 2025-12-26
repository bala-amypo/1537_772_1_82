package com.example.demo.controller;

import com.example.demo.dto.ProductivityMetricDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.ProductivityMetricRecord;
import com.example.demo.service.ProductivityMetricService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metrics")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class ProductivityMetricController {

    private final ProductivityMetricService service;

    public ProductivityMetricController(ProductivityMetricService service) {
        this.service = service;
    }

    @PostMapping
    public ProductivityMetricDto record(@RequestBody ProductivityMetricDto dto) {
        return toDto(service.recordMetric(toEntity(dto)));
    }

    @PutMapping("/{id}")
    public ProductivityMetricDto update(
            @PathVariable Long id,
            @RequestBody ProductivityMetricDto dto) {
        return toDto(service.updateMetric(id, toEntity(dto)));
    }

    @GetMapping("/{id}")
    public ProductivityMetricDto getOne(@PathVariable Long id) {
        return service.getMetricById(id)
                .map(this::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Metric not found"));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ProductivityMetricDto> byEmployee(
            @PathVariable Long employeeId) {

        return service.getMetricsByEmployee(employeeId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ProductivityMetricDto> all() {
        return service.getAllMetrics()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductivityMetricRecord toEntity(ProductivityMetricDto dto) {
        ProductivityMetricRecord m = new ProductivityMetricRecord();
        m.setEmployeeId(dto.getEmployeeId());
        m.setDate(dto.getDate());
        m.setHoursLogged(dto.getHoursLogged());
        m.setTasksCompleted(dto.getTasksCompleted());
        m.setMeetingsAttended(dto.getMeetingsAttended());
        m.setRawDataJson(dto.getRawDataJson());
        return m;
    }

    private ProductivityMetricDto toDto(ProductivityMetricRecord m) {
        ProductivityMetricDto dto = new ProductivityMetricDto();
        dto.setId(m.getId());
        dto.setEmployeeId(m.getEmployeeId());
        dto.setDate(m.getDate());
        dto.setHoursLogged(m.getHoursLogged());
        dto.setTasksCompleted(m.getTasksCompleted());
        dto.setMeetingsAttended(m.getMeetingsAttended());
        dto.setProductivityScore(m.getProductivityScore());
        dto.setRawDataJson(m.getRawDataJson());
        dto.setSubmittedAt(m.getSubmittedAt());
        return dto;
    }
}
