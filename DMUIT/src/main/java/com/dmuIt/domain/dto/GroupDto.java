package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Group;
import lombok.*;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class GroupDto {
    private Long groupId;
    private String groupName;
    private String nickname;
    private String content;
    private Integer allPersonnel;
    private String githubUrl;

    public GroupDto(Group entity) {
        this.groupId = entity.getGroupId();
        this.groupName = entity.getGroupName();
        this.content = entity.getContent();
        this.allPersonnel = entity.getAllPersonnel();
        this.githubUrl = entity.getGithubUrl();
    }

    @Getter
    @Setter
    public static class Response {
        private Long groupId;
        private String groupName;
        private String nickname;
        private Integer nowPersonnel;
        private String content;
    }
}
