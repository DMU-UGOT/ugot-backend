package com.dmuIt.domain.repository;

import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.entity.Bookmark;
import com.dmuIt.domain.entity.Community;

import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommunityRepository extends JpaRepository<Community, Long> {

    Page<Community> findAllByOrderByIdDesc(Pageable pageable);


}
