package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.dto.TeamDto;
import com.dmuIt.domain.entity.Community;
import com.dmuIt.domain.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommunityMapper {
    List<Community> ComsToComResponseDtos(List<Community> Community);
}
