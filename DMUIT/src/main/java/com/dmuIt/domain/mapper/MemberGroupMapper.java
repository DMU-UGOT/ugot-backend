package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.MemberGroupDto;
import com.dmuIt.domain.entity.MemberGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberGroupMapper {
    default MemberGroupDto.GroupResponse memberGroupToResponse(MemberGroup memberGroup) {
        if (memberGroup == null) {
            return null;
        } else {
            MemberGroupDto.GroupResponse groupResponse = new MemberGroupDto.GroupResponse();
            groupResponse.setGroupId(memberGroup.getGroup().getGroupId());
            groupResponse.setGroupName(memberGroup.getGroup().getGroupName());
            groupResponse.setNowPersonnel(memberGroup.getGroup().getNowPersonnel());
            groupResponse.setContent(memberGroup.getGroup().getContent());
            groupResponse.setGithubUrl(memberGroup.getGroup().getGithubUrl());
            return groupResponse;
        }
    }

    List<MemberGroupDto.GroupResponse> memberGroupsToMemberGroupResponseDtos(List<MemberGroup> memberGroups);

    default MemberGroupDto.MemberResponse memberToMemberResponse(MemberGroup memberGroup) {
        if (memberGroup == null) {
            return null;
        } else {
            MemberGroupDto.MemberResponse response = new MemberGroupDto.MemberResponse();
            response.setMemberId(memberGroup.getMember().getMemberId());
            response.setNickname(memberGroup.getMember().getNickname());
            response.setInterests(memberGroup.getMember().getSkill().get(0));
            response.setGroupName(memberGroup.getGroup().getGroupName());
            response.setGitHubLink(memberGroup.getMember().getGitHubLink());
            response.setPersonalBlogLink(memberGroup.getMember().getPersonalBlogLink());
            return response;
        }
    }

    List<MemberGroupDto.MemberResponse> membersToMemberResponse(List<MemberGroup> memberGroups);
}
