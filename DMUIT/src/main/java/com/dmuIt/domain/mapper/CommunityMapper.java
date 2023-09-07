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
    default CommunityResponseDto comToComResponseDto(Community community) {
        if (community == null) {
            return null;
        } else {
            CommunityResponseDto response = new CommunityResponseDto();
            if (community.getId() != null) {
                response.setId(community.getId());
            }
            response.setTitle(community.getTitle());
            response.setContent(community.getContent());
            response.setViewCount(community.getViewCount());
            response.setVoteCount(community.getVoteCount());
            response.setCreated_at(community.getCreatedAt());
            return response;
        }
    }

    List<CommunityResponseDto> ComsToComResponseDtos(List<Community> Community);
}
