package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findAllByMemberAndTypeOrderByCreatedAtDesc(Member member, String type);

    void deleteAllByMemberAndType(Member member, String type);
}
