
package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Team;

import com.dmuIt.domain.repository.TeamRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
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


    @Transactional
    public List<Team> searchPosts(String keyword) {
        return teamRepository.findAllSearch(keyword).stream()
                .map(Team::new)
                .collect(Collectors.toList());

    }

    //게시글리스트처리
    public Page<Team> teamList(Pageable pageable){
        //findAll : 테스트보드라는 클래스가 담긴 List를 반환하는것을 확인할수있다
        return teamRepository.findAll(pageable);
    }

    /*검색기능-2*/
    //검색
    public Page<Team> teamSearchList(String searchKeyword, Pageable pageable){
        return teamRepository.findByTitleContaining(searchKeyword, pageable);
    }
}


