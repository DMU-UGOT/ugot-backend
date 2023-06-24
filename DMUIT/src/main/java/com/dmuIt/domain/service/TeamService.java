package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    public void updateTeam(Team team) {
        Team findTeam = findVerifiedTeam(team.getId());
        Optional.ofNullable(team.getTitle())
                .ifPresent(title -> findTeam.setTitle(title));
        Optional.ofNullable(team.getContent())
                .ifPresent(content -> findTeam.setContent(content));
        Optional.ofNullable(team.getField())
                .ifPresent(field -> findTeam.setField(field));
        Optional.ofNullable(team.get_class())
                .ifPresent(_class -> findTeam.set_class(_class));
        Optional.ofNullable(team.getPersonnel())
                .ifPresent(personnel -> findTeam.setPersonnel(personnel));
        findTeam.setModifiedAt(LocalDateTime.now());
        teamRepository.save(findTeam);
    }

    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Team findTeam = optionalTeam.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TEAM_NOT_FOUND));
        return findTeam;
    }
}
