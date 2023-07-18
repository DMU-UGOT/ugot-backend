package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.mapper.TeamMapper;
import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    public void createTeam(@RequestBody @Valid TeamDto.Post teamPostDto) {
        Team team = teamMapper.teamPostDtoToTeam(teamPostDto);
        teamService.createTeam(team);
    }

    @PatchMapping("/{team-id}")
    public void patchTeam(@RequestBody @Valid TeamDto.Patch teamPatchDto,
                          @PathVariable("team-id") long teamId) {
        Team team = teamMapper.teamPatchDtoToTeam(teamPatchDto);
        team.setId(teamId);
        teamService.updateTeam(team);
    }

    @GetMapping("/{team-id}")
    public TeamDto.Response getTeam(@PathVariable("team-id") long teamId) {
        Team team = teamService.findTeam(teamId);
        TeamDto.Response response = teamMapper.teamToTeamResponseDto(team);
        response.setTeamId(teamId);
        return response;
    }

    @DeleteMapping("/{team-id}")
    public void deleteTeam(@PathVariable("team-id") long teamId) {
        teamService.removeTeam(teamId);
    }


    @RequiredArgsConstructor
    public class PageController {

        @Autowired
        TeamRepository postRepository;

        @CrossOrigin(origins = "*", allowedHeaders = "*")
        @GetMapping("/post/page")
        public Page<TeamDto> paging(@PageableDefault(size=5, sort="createdTime") Pageable pageRequest) {

            Page<Team> teamList = postRepository.findAll(pageRequest);

            Page<TeamDto> pagingList = teamList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.get_class(), team.getField(),
                            team.getPersonnel()
                    ));

            return pagingList;
        }

        @CrossOrigin(origins = "*", allowedHeaders = "*")
        @GetMapping("/post/page/search")
        public Page<TeamDto> searchPaging(
                @RequestParam String title,
                @RequestParam String content,
                @PageableDefault(size=5, sort="createdTime") Pageable pageRequest) {

            Page<Team> postList = postRepository.findAllSearch(title,content,pageRequest);

            Page<TeamDto> pagingList = postList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.get_class(), team.getField(),
                            team.getPersonnel()
                    ));

            return pagingList;
        }

}}
