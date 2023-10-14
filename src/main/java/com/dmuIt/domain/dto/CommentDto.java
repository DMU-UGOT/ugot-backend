package com.dmuIt.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class CommentDto {
    @Getter
    @Setter
    public static class Response {
        private Long commentId;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
    }
}
