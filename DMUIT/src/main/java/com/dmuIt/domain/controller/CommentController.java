package com.dmuIt.domain.controller;

import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.repository.CommentRepository;
import com.dmuIt.domain.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    CommentRepository commentRepository;

  /*  @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/post/{id}/comment")
    public List<Comment> getPostComments(@PathVariable Long id){
        Community community = communityRepository.findById(id).get();
        return commentRepository.findCommentsByPost(community);
    }*/

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/com/{id}/comment")
    public Comment createComment(@PathVariable Long id, @RequestBody Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        commentRepository.save(comment);
        return comment;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/post/{id}/comment/{commentID}")
    public Comment updateComment(@PathVariable Long id,@PathVariable Long commentID, @RequestBody Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        Comment newComment = commentRepository.findById(commentID).get();
        newComment.setContent(comment.getContent());
        newComment.setMember(comment.getMember());
        return newComment;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/post/{id}/comment/{commentID}")
    public String deleteComment(@PathVariable Long id, @PathVariable Long commentID){
        commentRepository.deleteById(commentID);
        return "Comment Delete Success!";
    }
}
