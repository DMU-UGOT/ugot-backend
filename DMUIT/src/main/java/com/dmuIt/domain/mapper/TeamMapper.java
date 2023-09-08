package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {
    Team teamPostDtoToTeam(TeamDto.Post teamPostDto);
    Team teamPatchDtoToTeam(TeamDto.Patch teamPatchDto);
    default TeamDto.Response teamToResponse(Team team) {
        if (team == null) {
            return null;
        } else {
            TeamDto.Response response = new TeamDto.Response();
            if (team.getId() != null) {
                response.setTeamId(team.getId());
            }
            response.setTitle(team.getTitle());
            response.setContent(team.getContent());
            response.setField(team.getField());
            response.set_class(team.get_class());
            response.setNickname(team.getMember().getNickname());
            response.setBookmarked(team.getBookmarked());
            response.setAllPersonnel(team.getAllPersonnel());
            response.setNowPersonnel(team.getNowPersonnel());
            response.setViewCount(team.getViewCount());
            response.setKakaoOpenLink(team.getKakaoOpenLink());
            response.setGitHubLink(team.getGitHubLink());
            response.setCreatedAt(team.getCreatedAt());
            return response;
        }
    }
    List<TeamDto.Response> teamsToTeamResponseDtos(List<Team> teams);
}
