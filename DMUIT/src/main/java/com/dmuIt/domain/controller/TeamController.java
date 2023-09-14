package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.PageInfo;
import com.dmuIt.domain.dto.FindAllDto;
import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.entity.TeamBookmark;
import com.dmuIt.domain.mapper.TeamMapper;
import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    public void createTeam(HttpServletRequest request, @RequestBody @Valid TeamDto.Post teamPostDto) {
        Team team = teamMapper.teamPostDtoToTeam(teamPostDto);
        teamService.createTeam(request, team);
    }

    @PatchMapping("/{team-id}")
    public void patchTeam(HttpServletRequest request,
                          @RequestBody @Valid TeamDto.Patch teamPatchDto,
                          @PathVariable("team-id") long teamId) {
        Team team = teamMapper.teamPatchDtoToTeam(teamPatchDto);
        team.setId(teamId);
        teamService.updateTeam(request, team);
    }

    @GetMapping("/{team-id}")
    public TeamDto.Response getTeam(@PathVariable("team-id") long teamId) {
        Team team = teamService.findTeam(teamId);
        return teamMapper.teamToResponse(team);

    }

    @GetMapping
    public FindAllDto getTeams(@Positive @RequestParam int page,
                               @Positive @RequestParam int size) {
        // page information
        Page<Team> teamPage = teamService.findTeams(page - 1, size);
        PageInfo pageInfo = new PageInfo(page, size, (int) teamPage.getTotalElements(), teamPage.getTotalPages());

        // team 반환 + dto로 변환
        List<Team> teams = teamPage.getContent();
        List<TeamDto.Response> responses = teamMapper.teamsToTeamResponseDtos(teams);

        return new FindAllDto(responses, pageInfo);
    }

    @GetMapping("/myTeams")
    public List<TeamDto.Response> findMyTeams(HttpServletRequest request) {
        return teamMapper.teamsToTeamResponseDtos(teamService.findMyTeams(request));
    }

    @DeleteMapping("/{team-id}")
    public void deleteTeam(HttpServletRequest request, @PathVariable("team-id") long teamId) {
        teamService.removeTeam(request, teamId);
    }

    @PostMapping("/bookmark/{team-id}")
    public void bookmarkTeam(HttpServletRequest request, @PathVariable("team-id") long teamId) {
        teamService.bookmarkTeam(request, teamId);
    }

    @GetMapping("/bookmark")
    public List<TeamDto.Response> findMyTeamBookmarks(HttpServletRequest request) {
        return teamMapper.teamsToTeamResponseDtos(teamService.findMyTeamBookmarks(request));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/post/search")
    public Page<TeamDto> searchPaging(@Param("keyword") String keyword, @PageableDefault(size = 5) Pageable pageRequest) {

        Page<TeamDto> pagingList = null;
        if(keyword == null) {
            Page<Team> teamList = teamRepository.findAllSearch("", pageRequest);
            pagingList = teamList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.getField(), team.get_class(),
                            team.getAllPersonnel(), team.getNowPersonnel(), team.getGoal(), team. getLanguage(),
                            team.getViewCount(), team.getBookmarked(),
                            team.getKakaoOpenLink(), team.getGitHubLink(), team.getCreatedAt()

                    ));
        }else{
            Page<Team> teamList = teamRepository.findAllSearch(keyword, pageRequest);
            pagingList = teamList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.getField(), team.get_class(),
                            team.getAllPersonnel(), team.getNowPersonnel(), team.getGoal(), team. getLanguage(),
                            team.getViewCount(), team.getBookmarked(),
                            team.getKakaoOpenLink(), team.getGitHubLink(), team.getCreatedAt()


                    ));
        }
        return pagingList;

    }
}
