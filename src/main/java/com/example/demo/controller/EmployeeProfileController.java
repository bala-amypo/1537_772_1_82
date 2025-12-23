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
            @PathVariable Long id,
            @RequestParam boolean active) {
        return service.updateEmployeeStatus(id, active);
    }

    @GetMapping("/lookup/{employeeId}")
    public EmployeeProfile lookup(@PathVariable String employeeId) {
        return service.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}
 