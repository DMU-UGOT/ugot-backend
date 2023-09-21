package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupId(Long groupLikeId);
}
