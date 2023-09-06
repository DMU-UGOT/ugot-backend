package com.dmuIt.domain.controller;

import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.repository.CommentRepository;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    /**
    댓글 조회
    */

    @GetMapping("/com/{id}/comment")
    public List<Comment> getPostComments(@PathVariable Long id){
        return commentService.getComments(id);
    }

    /**
     댓글 추가
     */
    @PostMapping("/com/{id}/comment")
    public void createComment(HttpServletRequest request,  @PathVariable Long id, @RequestBody Comment comment){
        commentService.create(request, id, comment);
    }


    /**
     댓글 수정 -> 수정 아직 안됨.
     */

    @PatchMapping("/com/{id}/comment/{commentID}")
    public void update(HttpServletRequest request, @PathVariable Long id, @PathVariable Long commentID, @RequestBody Comment comment){
        commentService.update(request, id,commentID,comment);
    }

    /**
     댓글 삭제
     */
    @DeleteMapping("/com/{id}/comment/{commentID}")
    public void deleteComment(HttpServletRequest request, @PathVariable Long id, @PathVariable Long commentID){
        commentService.delete(request, id, commentID);
    }

}
