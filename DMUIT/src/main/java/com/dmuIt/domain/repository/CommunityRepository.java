package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Community;

import com.dmuIt.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query(
            value = "SELECT * FROM community p WHERE p.comId LIKE :comId", nativeQuery = true
    )
    Community findMemberById(Long comId);
    Page<Community> findAllByOrderByIdDesc(Pageable pageable);

    List<Community> findCommunitiesByMember(Member member);
}
