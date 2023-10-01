package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
    Favorite findFavoriteByMemberAndGroup(Member member, Group group);
    List<Favorite> findFavoritesByMember(Member member);
}
