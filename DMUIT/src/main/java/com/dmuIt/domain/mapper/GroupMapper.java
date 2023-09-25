package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    GroupDto.Response groupToResponse(Group group);
}
