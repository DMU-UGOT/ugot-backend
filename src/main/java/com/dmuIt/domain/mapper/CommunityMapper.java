package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.CommunityResponseDto;
import com.dmuIt.domain.entity.Community;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommunityMapper {
    default CommunityResponseDto comToComResponseDto(Community community) {
        if ( community == null ) {
            return null;
        }

        CommunityResponseDto communityResponseDto = new CommunityResponseDto();

        communityResponseDto.setId( community.getId() );
        communityResponseDto.setTitle( community.getTitle() );
        communityResponseDto.setContent( community.getContent() );
        communityResponseDto.setViewCount( community.getViewCount() );
        communityResponseDto.setCommentCount((long) community.getComments().size());
        communityResponseDto.setNickname(community.getMember().getNickname());
        communityResponseDto.setCreated_at(community.getCreatedAt());
        communityResponseDto.setMemberId(community.getMember().getMemberId());

        return communityResponseDto;
    }
    List<CommunityResponseDto> ComsToComResponseDtos(List<Community> Community);
}
