package com.dmuIt.domain.dto;

import lombok.Getter;
import lombok.Setter;

public class FavoriteDto {
    @Getter
    @Setter
    public static class Response {
        private Long groupId;
        private String groupName;
        private String gitHubUrl;
    }
}