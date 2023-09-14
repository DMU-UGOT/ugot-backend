package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository  extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findHeartByUserAndCampaignId(Member member, String groupId);
}
