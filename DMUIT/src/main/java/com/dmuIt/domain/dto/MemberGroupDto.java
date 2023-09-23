package com.dmuIt.domain.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberGroupDto {
    @Getter
    @Setter
    public static class GroupResponse {
        private Long groupId;
        private String groupName;
        private String content;
        private Integer nowPersonnel;
    }

    @Getter
    @Setter
    public static class MemberResponse {
        private Long memberId;
        private String nickname;
        private String interests;
        private String groupName;
        private String gitHubLink;
        private String personalBlogLink;
    }
}
