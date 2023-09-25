package com.dmuIt.domain.dto;

import lombok.*;

public class FavoriteDto {
    @Getter
    @Setter
    public static class Response {
        private Long groupId;
        private String groupName;
        private String gitHubUrl;
    }
}