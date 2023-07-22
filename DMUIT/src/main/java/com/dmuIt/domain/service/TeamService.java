
package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Team;

import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public void createTeam(Team team) {
        teamRepository.save(team);
    }

    @Transactional
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

    public void removeTeam(long teamId) {
        teamRepository.delete(findVerifiedTeam(teamId));
    }

    public Team findTeam(long teamId) {
        Team team = findVerifiedTeam(teamId);
        team.setViewCount(team.getViewCount() + 1);
        return teamRepository.save(team);
    }


    public Team findVerifiedTeam(long teamId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Team findTeam = optionalTeam.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TEAM_NOT_FOUND));
        return findTeam;
    }

  /*  public Page<Team> searchTeam(int page, int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return teamRepository.findAllSearch(pageRequest);
    }*/

    //게시글 리스트
    public Page<Team> getPostList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Team> postList = teamRepository.findAll(pageable);

        return postList;
    }

}

