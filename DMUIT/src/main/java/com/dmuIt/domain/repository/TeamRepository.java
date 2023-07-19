package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findAll(Pageable pageable);
    @Query(
            value = "SELECT p FROM Team p WHERE p.title LIKE %:title% OR p.content LIKE %:content%",
            countQuery = "SELECT COUNT(p.id) FROM Team p WHERE p.title LIKE %:title% OR p.content LIKE %:content%"
    )
    Page<Team> findAllSearch(String title, String content, Pageable pageable);

}