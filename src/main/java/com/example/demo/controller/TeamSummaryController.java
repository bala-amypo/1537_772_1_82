package com.example.demo.controller;

import com.example.demo.model.TeamSummaryRecord;
import com.example.demo.service.TeamSummaryService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/team-summaries")
public class TeamSummaryController {

    private final TeamSummaryService service;

    public TeamSummaryController(TeamSummaryService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public TeamSummaryRecord generate(
            @RequestParam String teamName,
            @RequestParam LocalDate date) {
        return service.generateSummary(teamName, date);
    }

    @GetMapping("/team/{teamName}")
    public List<TeamSummaryRecord> byTeam(@PathVariable String teamName) {
        return service.getSummariesByTeam(teamName);
    }

    @GetMapping
    public List<TeamSummaryRecord> all() {
        return service.getAllSummaries();
    }
}
