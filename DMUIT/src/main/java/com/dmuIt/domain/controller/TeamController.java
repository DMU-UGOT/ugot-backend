package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.mapper.TeamMapper;
import com.dmuIt.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/list")
    public String teamList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable,
                            String searchKeyword){

        Page<Team> list = null;

        /*searchKeyword = 검색하는 단어*/
        if(searchKeyword == null){
            list = teamService.teamList(pageable); //기존의 리스트보여줌
        }else{
            list = teamService.teamSearchList(searchKeyword, pageable); //검색리스트반환
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
        int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        //BoardService에서 만들어준 teamList가 반환되는데, list라는 이름으로 받아서 넘기겠다는 뜻
        model.addAttribute("list" , list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "teamList";
    }

}
