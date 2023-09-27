package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Team;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-27T21:51:35+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class TeamMapperImpl implements TeamMapper {

    @Override
    public Team teamPostDtoToTeam(TeamDto.Post teamPostDto) {
        if ( teamPostDto == null ) {
            return null;
        }

        Team team = new Team();

        team.setTitle( teamPostDto.getTitle() );
        team.setContent( teamPostDto.getContent() );
        team.setField( teamPostDto.getField() );
        team.set_class( teamPostDto.get_class() );
        team.setGoal( teamPostDto.getGoal() );
        team.setLanguage( teamPostDto.getLanguage() );
        team.setKakaoOpenLink( teamPostDto.getKakaoOpenLink() );
        team.setGitHubLink( teamPostDto.getGitHubLink() );

        return team;
    }

    @Override
    public Team teamPatchDtoToTeam(TeamDto.Patch teamPatchDto) {
        if ( teamPatchDto == null ) {
            return null;
        }

        Team team = new Team();

        team.setTitle( teamPatchDto.getTitle() );
        team.setContent( teamPatchDto.getContent() );
        team.setField( teamPatchDto.getField() );
        team.set_class( teamPatchDto.get_class() );
        team.setGoal( teamPatchDto.getGoal() );
        team.setLanguage( teamPatchDto.getLanguage() );
        team.setKakaoOpenLink( teamPatchDto.getKakaoOpenLink() );
        team.setGitHubLink( teamPatchDto.getGitHubLink() );

        return team;
    }

    @Override
    public List<TeamDto.Response> teamsToTeamResponseDtos(List<Team> teams) {
        if ( teams == null ) {
            return null;
        }

        List<TeamDto.Response> list = new ArrayList<TeamDto.Response>( teams.size() );
        for ( Team team : teams ) {
            list.add( teamToResponse( team ) );
        }

        return list;
    }
}
