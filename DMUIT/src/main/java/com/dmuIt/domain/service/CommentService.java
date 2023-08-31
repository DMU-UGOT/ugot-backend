package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    /*@Transactional
    public Long update(final Long id, final Long CommentID, final Comment params){

    }*/
}
