package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByCommunity(Community community);
}
