package com.dmuIt.domain.repository;

import com.dmuIt.domain.entity.Bookmark;
import com.dmuIt.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
