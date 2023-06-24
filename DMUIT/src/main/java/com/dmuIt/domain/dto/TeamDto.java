package com.dmuIt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TeamDto {

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Post {
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer personnel;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer personnel;
    }
}
