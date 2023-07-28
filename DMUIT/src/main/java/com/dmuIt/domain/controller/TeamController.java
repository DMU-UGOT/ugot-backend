package com.dmuIt.domain.controller;

import com.dmuIt.domain.PageInfo;
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
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    @Autowired
    TeamRepository teamRepository;
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

    //페이징
    @GetMapping("/post")
    public Page<Team> getPostList(@RequestParam("page") int page) {
        Page<Team> resultList = teamService.getPostList(page, 5);
        return resultList;
    }

    //검색
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/post/search")
    public Page<TeamDto> searchPaging(@Param("keyword") String keyword, @PageableDefault(size = 5) Pageable pageRequest) {

        Page<TeamDto> pagingList = null;
        if(keyword == null) {
            Page<Team> teamList = teamRepository.findAllSearch("", pageRequest);
            pagingList = teamList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.get_class(), team.getField(),
                            team.getPersonnel(), team.getViewCount(), team.getBookmarked()
                    ));
        }else{
            Page<Team> teamList = teamRepository.findAllSearch(keyword, pageRequest);
            pagingList = teamList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.get_class(), team.getField(),
                            team.getPersonnel(), team.getViewCount(), team.getBookmarked()
                    ));
        }
        return pagingList;

    }
}

