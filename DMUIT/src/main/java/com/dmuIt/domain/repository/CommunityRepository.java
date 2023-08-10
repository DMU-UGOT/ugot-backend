package com.dmuIt.domain.repository;

import com.dmuIt.domain.dto.CommunityRequestDto;
import com.dmuIt.domain.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

}
