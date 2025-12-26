package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @PostMapping
    public EmployeeProfile create(@RequestBody EmployeeProfile employee) {
        return service.createEmployee(employee);
    }

    @GetMapping("/{id}")
    public EmployeeProfile getById(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeProfile> getAll() {
        return service.getAllEmployees();
    }

    @PutMapping("/{id}/status")
    public EmployeeProfile updateStatus(
            @Ppackage com.example.demo.controller;

import com.example.demo.dto.EmployeeProfileDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EmployeeProfile;
import com.example.demo.service.EmployeeProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
public class EmployeeProfileController {

    private final EmployeeProfileService service;

    public EmployeeProfileController(EmployeeProfileService service) {
        this.service = service;
    }

    @PostMapping
    public EmployeeProfileDto create(@RequestBody EmployeeProfileDto dto) {
        EmployeeProfile saved = service.createEmployee(toEntity(dto));
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public EmployeeProfileDto getById(@PathVariable Long id) {
        return toDto(service.getEmployeeById(id));
    }

    @GetMapping
    public List<EmployeeProfileDto> getAll() {
        return service.getAllEmployees()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/status")
    public EmployeeProfileDto updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return toDto(service.updateEmployeeStatus(id, active));
    }

    @GetMapping("/lookup/{employeeId}")
    public EmployeeProfileDto lookup(@PathVariable String employeeId) {
        return service.findByEmployeeId(employeeId)
                .map(this::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));
    }

    private EmployeeProfile toEntity(EmployeeProfileDto dto) {
        EmployeeProfile e = new EmployeeProfile();
        e.setEmployeeId(dto.getEmployeeId());
        e.setFullName(dto.getFullName());
        e.setEmail(dto.getEmail());
        e.setTeamName(dto.getTeamName());
        e.setRole(dto.getRole());
        e.setActive(dto.getActive());
        return e;
    }

    private EmployeeProfileDto toDto(EmployeeProfile e) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(e.getId());
        dto.setEmployeeId(e.getEmployeeId());
        dto.setFullName(e.getFullName());
        dto.setEmail(e.getEmail());
        dto.setTeamName(e.getTeamName());
        dto.setRole(e.getRole());
        dto.setActive(e.getActive());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
athVariable Long id,
            @RequestParam boolean active) {
        return service.updateEmployeeStatus(id, active);
    }

    @GetMapping("/lookup/{employeeId}")
    public EmployeeProfile lookup(@PathVariable String employeeId) {
        return service.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}
 