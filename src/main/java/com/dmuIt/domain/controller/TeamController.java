package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.*;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.mapper.MemberGroupMapper;
import com.dmuIt.domain.mapper.TeamMapper;
import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.domain.service.GroupService;
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
    private final MemberGroupMapper memberGroupMapper;
    private final GroupService groupService;

    @PostMapping
    public void createTeam(HttpServletRequest request,
                           @RequestBody @Valid TeamDto.Post teamPostDto) {
        Team team = teamMapper.teamPostDtoToTeam(teamPostDto);
        team.setGroup(groupService.verifiedGroup(teamPostDto.getGroupId()));
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

    @PatchMapping("/{team-id}/refresh")
    public void refreshTeam(HttpServletRequest request,
                            @PathVariable("team-id") long teamId) {
        teamService.refreshTeam(request, teamId);
    }

    @GetMapping("/{team-id}")
    public TeamDto.Response getTeam(@PathVariable("team-id") long teamId) {
        Team team = teamService.findTeam(teamId);
        return teamMapper.teamToResponse(team);

    }

    @GetMapping("/createdAt")
    public FindAllDto<?> getTeamsOrderByCreatedAt(@Positive @RequestParam int page,
                               @Positive @RequestParam int size) {
        // page information
        Page<Team> teamPage = teamService.findTeamsOrderByCreatedAt(page - 1, size);
        PageInfo pageInfo = getPageInfo(page, size, teamPage);

        // team 반환 + dto로 변환
        List<Team> teams = teamPage.getContent();
        List<TeamDto.Response> responses = teamMapper.teamsToTeamResponseDtos(teams);

        return new FindAllDto<>(responses, pageInfo);
    }

    @GetMapping("/viewCount")
    public FindAllDto<?> getTeamsOrderByViewCount(@Positive @RequestParam int page,
                               @Positive @RequestParam int size) {
        // page information
        Page<Team> teamPage = teamService.findTeamsOrderByViewCount(page - 1, size);
        PageInfo pageInfo = getPageInfo(page, size, teamPage);

        // team 반환 + dto로 변환
        List<Team> teams = teamPage.getContent();
        List<TeamDto.Response> responses = teamMapper.teamsToTeamResponseDtos(teams);

        return new FindAllDto<>(responses, pageInfo);
    }

    @GetMapping("/allPersonnel")
    public FindAllDto<?> getTeamsOrderByAllPersonnel(@Positive @RequestParam int page,
                               @Positive @RequestParam int size) {
        // page information
        Page<Team> teamPage = teamService.findTeamsOrderByAllPersonnel(page - 1, size);
        PageInfo pageInfo = getPageInfo(page, size, teamPage);

        // team 반환 + dto로 변환
        List<Team> teams = teamPage.getContent();
        List<TeamDto.Response> responses = teamMapper.teamsToTeamResponseDtos(teams);

        return new FindAllDto<>(responses, pageInfo);
    }

    private static PageInfo getPageInfo(int page, int size, Page<Team> teamPage) {
        return new PageInfo(page, size, (int) teamPage.getTotalElements(), teamPage.getTotalPages());
    }

    @GetMapping("/{team-id}/findMembers")
    public List<MemberGroupDto.MemberResponse> findMembers(@PathVariable("team-id") long teamId) {
        return memberGroupMapper.membersToMemberResponse(teamService.findMembers(teamId));
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
    public Page<TeamDto> searchPaging(HttpServletRequest request, @Param("keyword") String keyword, @PageableDefault(size = 5) Pageable pageRequest) {
        teamService.saveTeamSearchKeyword(request, keyword);
        Page<TeamDto> pagingList = null;
        if(keyword.length() < 2) {
            throw new IllegalArgumentException("검색어는 두 글자 이상이어야 합니다.");
        }else{
            Page<Team> teamList = teamRepository.findAllSearch(keyword, pageRequest);
            pagingList = teamList.map(
                    team -> new TeamDto(
                            team.getId(), team.getTitle(), team.getContent(),
                            team.getField(), team.get_class(),
                            team.getGroup().getAllPersonnel(), team.getGroup().getNowPersonnel(), team.getGoal(), team. getLanguage(),
                            team.getViewCount(), team.getBookmarked(),
                            team.getKakaoOpenLink(), team.getGitHubLink(), team.getCreatedAt(), team.getMember().getMemberId()
                    ));
        }
        return pagingList;

    }

    @GetMapping("/searchHistory")
    public List<SearchHistoryDto> getSearchHistory(HttpServletRequest request) {
        return teamService.getSearchHistory(request);
    }

    @DeleteMapping("/searchHistory/{keyword}")
    public void removeSearchHistory(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        teamService.removeSearchHistory(request, keyword);
    }

    @DeleteMapping("/searchHistory")
    public void removeAllSearchHistory(HttpServletRequest request) {
        teamService.removeAllSearchHistory(request);
    }
}
