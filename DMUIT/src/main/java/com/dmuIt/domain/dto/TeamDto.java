package com.dmuIt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TeamDto {

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
    }
}
