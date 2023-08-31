package com.dmuIt.domain.controller;

import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.repository.CommentRepository;
import com.dmuIt.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    /**
    댓글 조회
    */

    @GetMapping("/com/{id}/comment")
    public List<Comment> getPostComments(@PathVariable Long id){
        Community community = communityRepository.findById(id).get();
        return commentRepository.findCommentsByCommunity(community);
    }

    /**
     댓글 추가
     */
    @PostMapping("/com/{id}/comment")
    public Comment createComment(@PathVariable Long id, @RequestBody Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        return commentRepository.save(comment);
    }


    /**
     댓글 수정 -> 수정 아직 안됨.
     */

   /* public Comment updateComment(@PathVariable Long id, @PathVariable Long commentID, @RequestBody Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        Comment newComment = commentRepository.findById(commentID).get();
        newComment.setContent(comment.getContent());
        newComment.setStatus(comment.getStatus());
        return newComment;
    }*/
    @PatchMapping("/com/{id}/comment/{commentID}")
    public Long update(@PathVariable Long id, @PathVariable Long commentID, @RequestBody Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        Comment newComment = commentRepository.findById(commentID).orElseThrow();
        newComment.update(comment.getContent(),comment.getStatus());
        return id;
    }

    /**
     댓글 삭제
     */
    @DeleteMapping("/com/{id}/comment/{commentID}")
    public void deleteComment(@PathVariable Long id, @PathVariable Long commentID){
        commentRepository.deleteById(commentID);
    }
}
