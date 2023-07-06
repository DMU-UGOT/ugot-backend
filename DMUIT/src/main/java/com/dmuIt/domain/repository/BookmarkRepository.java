package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Bookmark;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByTeamAndMember(Team team, Member member);
    Bookmark findBookmarkByTeam(Team team);
}
