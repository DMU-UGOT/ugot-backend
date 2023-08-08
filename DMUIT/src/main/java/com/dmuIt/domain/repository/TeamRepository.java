package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAllByOrderByIdDesc(Pageable pageable);
    Page<Team> findAll(Pageable pageable);
    @Query(
            value = "SELECT p FROM Team p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%",
            countQuery = "SELECT COUNT(p.id) FROM Team p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%"
    )
    Page<Team> findAllSearch(@Param("keyword") String keyword, Pageable pageable);
}
