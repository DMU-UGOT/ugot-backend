package com.dmuIt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ClassChangeDto {
    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class Post {
        private String grade;
        private String currentClass;
        private String changeClass;
    }

    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    public static class Patch {
        private String grade;
        private String currentClass;
        private String changeClass;
        private String status;
    }

    @Getter
    @Setter
    public static class Response {
        private Long classChangeId;
        private String grade;
        private LocalDateTime createdAt;
        private String nickname;
        private String currentClass;
        private String changeClass;
        private String status;
    }
}
