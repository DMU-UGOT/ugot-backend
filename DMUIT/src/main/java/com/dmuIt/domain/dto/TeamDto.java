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
    private Integer personnel;
    private LocalDateTime createdTime;
    public TeamDto(Long teamId, String title, String content, String field,
                   String _class, Integer personnel, LocalDateTime createdTime) {
        this.teamId = teamId;
        this.title = title;
        this.content = content;
        this.field = field;
        this._class = _class;
        this.personnel = personnel;
        this.createdTime = createdTime;
    }
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



}
