package com.dmuIt.domain.controller;

import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.repository.CommentRepository;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @GetMapping("/{id}/comment")
    public List<Comment> getPostComments(@PathVariable Long id){
        return commentService.getComments(id);
    }

    @PostMapping("/{id}/comment")
    public Comment createComment(@PathVariable Long id, @RequestBody Comment comment){
        return commentService.create(id, comment);
    }

/*
    @PatchMapping("/{id}/comment/{commentID}")
    public Comment update(@PathVariable Long id, @PathVariable Long commentID, @RequestBody Comment comment){
        return commentService.update(id,commentID,comment);
    }
*/

    @DeleteMapping("/{id}/comment/{commentID}")
    public void deleteComment(@PathVariable Long id, @PathVariable Long commentID){
        commentRepository.deleteById(commentID);
    }

}
