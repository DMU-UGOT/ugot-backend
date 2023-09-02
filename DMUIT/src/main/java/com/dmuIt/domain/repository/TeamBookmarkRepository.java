package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import com.dmuIt.domain.entity.TeamBookmark;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamBookmarkRepository extends JpaRepository<TeamBookmark, Long> {
    TeamBookmark findByTeamAndMember(Team team, Member member);
    TeamBookmark findBookmarkByTeam(Team team);
}
