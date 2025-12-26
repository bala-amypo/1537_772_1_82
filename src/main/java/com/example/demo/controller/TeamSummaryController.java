package com.example.demo.controller;

import com.example.demo.dto.TeamSummaryDto;
import com.example.demo.model.TeamSummaryRecord;
import com.example.demo.service.TeamSummaryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/team-summaries")
@PreAuthorize("hasAuthority('ADMIN')")
public class TeamSummaryController {

    private final TeamSummaryService service;

    public TeamSummaryController(TeamSummaryService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public TeamSummaryDto generate(
            @RequestParam String teamName,
            @RequestParam LocalDate date) {

        return toDto(service.generateSummary(teamName, date));
    }

    @GetMapping("/team/{teamName}")
    public List<TeamSummaryDto> byTeam(@PathVariable String teamName) {
        return service.getSummariesByTeam(teamName)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<TeamSummaryDto> all() {
        return service.getAllSummaries()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TeamSummaryDto toDto(TeamSummaryRecord r) {
        TeamSummaryDto dto = new TeamSummaryDto();
        dto.setId(r.getId());
        dto.setTeamName(r.getTeamName());
        dto.setSummaryDate(r.getSummaryDate());
        dto.setAvgHoursLogged(r.getAvgHoursLogged());
        dto.setAvgTasksCompleted(r.getAvgTasksCompleted());
        dto.setAvgScore(r.getAvgScore());
        dto.setAnomalyCount(r.getAnomalyCount());
        dto.setGeneratedAt(r.getGeneratedAt());
        return dto;
    }
}
