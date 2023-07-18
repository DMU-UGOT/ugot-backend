package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;


public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findByTitleContaining(String searchKeyword, Pageable pageable);

}