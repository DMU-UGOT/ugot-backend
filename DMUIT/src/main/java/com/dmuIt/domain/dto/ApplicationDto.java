package com.dmuIt.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDto {
    @Getter
    @Setter
    public static class Response {
        private Long memberId;
        private String nickname;
        private String major;
        private Integer grade;
        private String _class;
        private String gitHubLink;
        private String personalBlogLink;
        private List<String> skill;
    }
}
