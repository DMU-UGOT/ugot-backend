package com.dmuIt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ConversationDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String content;
    }

    @Getter
    @Setter
    public static class Response {
        private Long conversationId;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
    }
}
