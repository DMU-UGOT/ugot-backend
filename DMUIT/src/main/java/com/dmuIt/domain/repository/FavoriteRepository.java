package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long>{
    void deleteByGroupLikeId(/*String loginId, */Long groupLikeId);
    //Boolean existsByUserLoginIdAndGoardId(String loginId, Long groupId);
   // List<Favorite> findAllByUserLoginId(String loginId);
}
