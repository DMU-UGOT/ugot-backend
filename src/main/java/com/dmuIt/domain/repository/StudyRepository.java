package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Page<Study> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Study> findAllByOrderByViewCountDesc(Pageable pageable);
    Page<Study> findAllByOrderByGroupAllPersonnelDesc(Pageable pageable);
    @Query(
            value = "SELECT p FROM Study p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%",
            countQuery = "SELECT COUNT(p.studyId) FROM Study p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%"
    )
    Page<Study> findAllSearch(@Param("keyword") String keyword, Pageable pageable);
    List<Study> findStudiesByMember(Member member);
}
