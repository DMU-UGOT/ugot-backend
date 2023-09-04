package com.dmuIt.domain.service;

import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.repository.CommentRepository;
import com.dmuIt.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;

    public List<Comment> getComments(final Long id) {
        Community community = communityRepository.findById(id).get();
        return commentRepository.findCommentsByCommunity(community);
    }

    public Comment create(final Long id, final Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment update(final Long id, final Long commentID, final Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        Comment newComment = commentRepository.findById(commentID).get();
        newComment.update(comment.getContent(), comment.getStatus());
        newComment.setModifiedAt(LocalDateTime.now());
        return newComment;
    }

}
