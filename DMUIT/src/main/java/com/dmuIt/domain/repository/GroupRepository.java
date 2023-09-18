package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupId(Long groupLikeId);
}
