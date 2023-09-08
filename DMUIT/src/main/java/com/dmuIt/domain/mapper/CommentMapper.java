package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.CommentDto;
import com.dmuIt.domain.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    default CommentDto.Response commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.Response response = new CommentDto.Response();

        response.setNickname(comment.getMember().getNickname());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());

        return response;
    }
    List<CommentDto.Response> commentsToCommentResponseDtos(List<Comment> comments);
}