package com.dmuIt.domain.controller;

import com.dmuIt.domain.dto.CommentDto;
import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/com/{community-id}/comment")
    public List<CommentDto.Response> getPostComments(@PathVariable("community-id") Long communityId){
        return commentService.getComments(communityId);
    }

    @PostMapping("/com/{community-id}/comment")
    public void createComment(HttpServletRequest request, @PathVariable("community-id") Long communityId, @RequestBody Comment comment){
        commentService.create(request, communityId, comment);
    }

    @PatchMapping("/com/{community-id}/comment/{comment-id}")
    public void update(HttpServletRequest request, @PathVariable("community-id") Long communityId, @PathVariable("comment-id") Long commentId, @RequestBody Comment comment){
        commentService.update(request, communityId, commentId, comment);
    }

    @DeleteMapping("/com/{community-id}/comment/{comment-id}")
    public void deleteComment(HttpServletRequest request, @PathVariable("community-id") Long communityId, @PathVariable("comment-id") Long commentId){
        commentService.delete(request, communityId, commentId);
    }
}
