package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findAllByOrderByIdDesc(Pageable pageable);
}
