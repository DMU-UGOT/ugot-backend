package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findNoticesByGroup(Group group);
}
