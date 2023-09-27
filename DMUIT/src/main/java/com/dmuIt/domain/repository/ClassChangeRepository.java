package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.ClassChange;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassChangeRepository extends JpaRepository<ClassChange, Long> {
    Page<ClassChange> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<ClassChange> findClassChangeByMember(Member member);
}
