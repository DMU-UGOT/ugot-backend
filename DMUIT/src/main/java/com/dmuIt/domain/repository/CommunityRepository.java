package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Community;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommunityRepository extends JpaRepository<Community, Long> {

    Page<Community> findAllByOrderByCreatedAtDesc(Pageable pageable);
    @Query(
            value = "SELECT p FROM Community p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%",
            countQuery = "SELECT COUNT(p.id) FROM Community p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%"
    )
    Page<Community> findAllSearch(@Param("keyword") String keyword, Pageable pageable);
    List<Community> findCommunitiesByMember(Member member);
}
