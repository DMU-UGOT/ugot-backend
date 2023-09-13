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
    private Long viewCount;
    private Long bookmarked;
    private String kakaoOpenLink;
    private String gitHubLink;
    private LocalDateTime createdAt;

    public StudyDto(Long studyId, String title, String content,
                    String isContact, Integer allPersonnel, Integer nowPersonnel,
                    Long viewCount, Long bookmarked, String kakaoOpenLink,
                    String gitHubLink, LocalDateTime createdAt) {
        this.studyId = studyId;
        this.title = title;
        this.content = content;
        this.isContact = isContact;
        this.allPersonnel = allPersonnel;
        this.nowPersonnel = nowPersonnel;
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
        private Integer allPersonnel;
        private String kakaoOpenLink;
        private String gitHubLink;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Patch {
        private String title;
        private String content;
        private String isContact;
        private Integer allPersonnel;
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
        private String kakaoOpenLink;
        private String gitHubLink;
        private Long viewCount;
        private LocalDateTime createdAt;
    }
}
