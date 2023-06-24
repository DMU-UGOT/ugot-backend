package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.mapper.TeamMapper;
import com.dmuIt.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    private void createTeam(@RequestBody @Valid TeamDto.Post teamPostDto) {
        Team team = teamMapper.teamPostDtoToTeam(teamPostDto);
        teamService.createTeam(team);
    }

    @PatchMapping("/{team-id}")
    private void patchTeam(@RequestBody @Valid TeamDto.Patch teamPatchDto,
                           @PathVariable("team-id") long teamId) {
        Team team = teamMapper.teamPatchDtoToTeam(teamPatchDto);
        team.setId(teamId);
        teamService.updateTeam(team);
    }
}
