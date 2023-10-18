package com.dmuIt.domain.service;

import com.dmuIt.domain.dto.CommentDto;
import com.dmuIt.domain.entity.Comment;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Member;
import com.dmuIt.domain.mapper.CommentMapper;
import com.dmuIt.domain.repository.CommentRepository;
import com.dmuIt.domain.repository.CommunityRepository;
import com.dmuIt.global.exception.BusinessLogicException;
import com.dmuIt.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final CommunityRepository communityRepository;
    private final CommunityService communityService;
    private final MemberService memberService;

    public List<CommentDto.Response> getComments(final Long id) {
        System.out.println("1");
        Community community = communityRepository.findById(id).get();
        System.out.println("1");
        return commentMapper.commentsToCommentResponseDtos(commentRepository.findCommentsByCommunity(community));
    }

    public void create(HttpServletRequest request, final Long id, final Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        comment.setMember(memberService.verifiedCurrentMember(request));
        commentRepository.save(comment);
    }

    @Transactional
    public void update(HttpServletRequest request, final Long id, final Long commentID, final Comment comment){
        Optional<Community> postItem = communityRepository.findById(id);
        comment.setCommunity(postItem.get());
        Comment newComment = commentRepository.findById(commentID).get();
        Member member = memberService.verifiedCurrentMember(request);
        if (newComment.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        newComment.setModifiedAt(LocalDateTime.now());
        newComment.update(comment.getContent());
    }

    public void delete(HttpServletRequest request, final Long id, final Long commentId) {
        communityService.verifiedCommunity(id);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment findComment = optionalComment.orElseThrow(()
                -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Member member = memberService.verifiedCurrentMember(request);
        if (findComment.getMember().getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.NO_PERMISSION);
        }
        commentRepository.delete(findComment);
    }
}
