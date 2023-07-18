package com.dmuIt.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

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

    @Getter
    @Setter
    public static class Response {
        private Long teamId;
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer personnel;
        private Long viewCount;
    }

        private Long teamId;
        private String title;
        private String content;
        private String field;
        private String _class;
        private Integer personnel;

        public TeamDto(Long teamId, String title, String content, String field,
                         String _class, Integer personnel) {
            this.teamId = teamId;
            this.title = title;
            this.content = content;
            this.field = field;
            this._class = _class;
            this.personnel = personnel;
        }


}
