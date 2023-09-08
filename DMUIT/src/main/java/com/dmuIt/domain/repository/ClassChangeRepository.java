package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.ClassChange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassChangeRepository extends JpaRepository<ClassChange, Long> {
    Page<ClassChange> findAllByOrderByClassChangeIdDesc(Pageable pageable);
}
