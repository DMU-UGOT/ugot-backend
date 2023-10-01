package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.MemberGroupDto;
import com.dmuIt.domain.entity.MemberGroup;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-01T23:03:50+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class MemberGroupMapperImpl implements MemberGroupMapper {

    @Override
    public List<MemberGroupDto.GroupResponse> memberGroupsToMemberGroupResponseDtos(List<MemberGroup> memberGroups) {
        if ( memberGroups == null ) {
            return null;
        }

        List<MemberGroupDto.GroupResponse> list = new ArrayList<MemberGroupDto.GroupResponse>( memberGroups.size() );
        for ( MemberGroup memberGroup : memberGroups ) {
            list.add( memberGroupToResponse( memberGroup ) );
        }

        return list;
    }

    @Override
    public List<MemberGroupDto.MemberResponse> membersToMemberResponse(List<MemberGroup> memberGroups) {
        if ( memberGroups == null ) {
            return null;
        }

        List<MemberGroupDto.MemberResponse> list = new ArrayList<MemberGroupDto.MemberResponse>( memberGroups.size() );
        for ( MemberGroup memberGroup : memberGroups ) {
            list.add( memberToMemberResponse( memberGroup ) );
        }

        return list;
    }
}
