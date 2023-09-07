package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommunityRepository extends JpaRepository<Community, Long> {

    Page<Community> findAllByOrderByIdDesc(Pageable pageable);


}
