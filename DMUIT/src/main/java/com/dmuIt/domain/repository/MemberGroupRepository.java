package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {
    List<MemberGroup> findMemberGroupsByMember(Member member);
    List<MemberGroup> findMemberGroupsByGroup(Group group);
    MemberGroup findMemberGroupByGroup(Group group);
    MemberGroup findMemberGroupByMember(Member member);
}
