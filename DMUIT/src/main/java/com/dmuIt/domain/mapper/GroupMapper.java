package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Group;
import com.dmuIt.domain.entity.MemberGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    default GroupDto.Response groupToResponse(Group group, List<MemberGroup> memberGroups) {
        if (group == null || memberGroups == null) {
            return null;
        } else {
            GroupDto.Response response = new GroupDto.Response();
            response.setGroupId(group.getGroupId());
            response.setGroupName(group.getGroupName());
            for (MemberGroup memberGroup : memberGroups) {
                if (memberGroup.getRole().equals("ADMIN")) {
                    response.setNickname(memberGroup.getMember().getNickname());
                }
            }
            response.setContent(group.getContent());
            response.setNowPersonnel(group.getNowPersonnel());
            return response;
        }
    }
}
