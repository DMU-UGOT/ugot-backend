package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class GroupDto {
    private Long groupId;
    private String groupName;
    private Integer person;
    private String githubUrl;

    public GroupDto(Group entity) {
        this.groupId = entity.getGroupId();
        this.groupName = entity.getGroupName();
        this.person = entity.getPerson();
        this.githubUrl = entity.getGithubUrl();
    }

}
