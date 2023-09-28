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
    private String goal;
    private String language;
    private Long viewCount;
    private Long bookmarked;
    private String kakaoOpenLink;
    private String gitHubLink;
    private LocalDateTime createdAt;
    private Long memberId;

    public TeamDto(Long teamId, String title, String content, String field,
                   String _class, Integer allPersonnel, Integer nowPersonnel, String goal, String language,
                   Long viewCount, Long bookmarked, String kakaoOpenLink, String gitHubLink, LocalDateTime createdAt, Long memberId) {
        this.teamId = teamId;
        this.title = title;
        this.content = content;
        this.field = field;
        this._class = _class;
        this.allPersonnel = allPersonnel;
        this.nowPersonnel = nowPersonnel;
        this.goal = goal;
        this.language = language;
        this.viewCount = viewCount;
        this.bookmarked = bookmarked;
        this.kakaoOpenLink = kakaoOpenLink;
        this.gitHubLink = gitHubLink;
        this.createdAt = createdAt;
        this.memberId = memberId;
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String content;
        private String field;
        private String _class;
        private String goal;
        private String language;
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
        private String field;
        private String _class;
        private String goal;
        private String language;
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
        private String nickname;
        private Long bookmarked;
        private Integer allPersonnel;
        private Integer nowPersonnel;
        private String goal;
        private String language;
        private String kakaoOpenLink;
        private String gitHubLink;
        private Long viewCount;
        private LocalDateTime createdAt;
        private Long memberId;
        private Long groupId;
        private String groupName;
    }
}
