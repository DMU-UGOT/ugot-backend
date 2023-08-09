package com.dmuIt.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class TeamDto {
    private Long teamId;
    private String title;
    private String content;
    private String field;
    private String _class;
    private Integer allPersonnel;
    private Integer nowPersonnel;
    private Long viewCount;
    private Long bookmarked;
    private String kakaoOpenLink;
    private String gitHubLink;

    public TeamDto(Long teamId, String title, String content, String field,
                   String _class, Integer allPersonnel, Integer nowPersonnel, Long viewCount, Long bookmarked,
                   String kakaoOpenLink, String gitHubLink) {
        this.teamId = teamId;
        this.title = title;
        this.content = content;
        this.field = field;
        this._class = _class;
        this.allPersonnel = allPersonnel;
        this.nowPersonnel = nowPersonnel;
        this.viewCount = viewCount;
        this.bookmarked = bookmarked;
        this.kakaoOpenLink = kakaoOpenLink;
        this.gitHubLink = gitHubLink;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer allPersonnel;
        private String kakaoOpenLink;
        private String gitHubLink;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer allPersonnel;
        private String kakaoOpenLink;
        private String gitHubLink;
    }

    @Getter
    @Setter
    public static class Response {
        private Long teamId;
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer allPersonnel;
        private Integer nowPersonnel;
        private String kakaoOpenLink;
        private String gitHubLink;
        private Long viewCount;
        private LocalDateTime createdAt;
    }
}
