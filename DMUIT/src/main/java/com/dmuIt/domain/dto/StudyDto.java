package com.dmuIt.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class StudyDto {
    private Long studyId;
    private String title;
    private String content;
    private String isContact;
    private Integer allPersonnel;
    private Integer nowPersonnel;
    private String subject;
    private String field;
    private Long viewCount;
    private Long bookmarked;
    private String kakaoOpenLink;
    private String gitHubLink;
    private LocalDateTime createdAt;

    public StudyDto(Long studyId, String title, String content,
                    String isContact, Integer allPersonnel, Integer nowPersonnel,
                    String subject, String field,
                    Long viewCount, Long bookmarked, String kakaoOpenLink,
                    String gitHubLink, LocalDateTime createdAt) {
        this.studyId = studyId;
        this.title = title;
        this.content = content;
        this.isContact = isContact;
        this.allPersonnel = allPersonnel;
        this.nowPersonnel = nowPersonnel;
        this.subject = subject;
        this.field = field;
        this.viewCount = viewCount;
        this.bookmarked = bookmarked;
        this.kakaoOpenLink = kakaoOpenLink;
        this.gitHubLink = gitHubLink;
        this.createdAt = createdAt;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String content;
        private String isContact;
        private String subject;
        private String field;
        private String kakaoOpenLink;
        private String gitHubLink;
        private Long groupId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private String title;
        private String content;
        private String isContact;
        private String subject;
        private String field;
        private String kakaoOpenLink;
        private String gitHubLink;
    }

    @Getter
    @Setter
    public static class Response {
        private Long studyId;
        private String title;
        private String content;
        private String isContact;
        private String nickname;
        private Long bookmarked;
        private Integer allPersonnel;
        private Integer nowPersonnel;
        private String subject;
        private String field;
        private String kakaoOpenLink;
        private String gitHubLink;
        private Long viewCount;
        private LocalDateTime createdAt;
    }
}
