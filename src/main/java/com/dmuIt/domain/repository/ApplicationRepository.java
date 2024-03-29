package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Application;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationsByGroup(Group group);

    Application findApplicationByMemberAndGroup(Member member, Group group);
}
