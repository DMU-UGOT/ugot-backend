package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Group;
import lombok.Getter;


@Getter
public class GroupDto {
    private Long id;
    private String groupName;
    private Integer person;
    private String githubUrl;

    public GroupDto(Group entity) {
        this.id = entity.getId();
        this.groupName = entity.getGroupName();
        this.person = entity.getPerson();
        this.githubUrl = entity.getGithubUrl();
    }

}
