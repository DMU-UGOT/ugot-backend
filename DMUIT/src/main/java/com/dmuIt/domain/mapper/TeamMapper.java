package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {
    Team teamPostDtoToTeam(TeamDto.Post teamPostDto);
    Team teamPatchDtoToTeam(TeamDto.Patch teamPatchDto);
    TeamDto.Response teamToTeamResponseDto(Team team);
}

