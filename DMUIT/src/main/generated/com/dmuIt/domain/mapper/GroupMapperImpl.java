package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.GroupDto;
import com.dmuIt.domain.entity.Group;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-23T22:56:36+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public GroupDto.Response groupToResponse(Group group) {
        if ( group == null ) {
            return null;
        }

        GroupDto.Response response = new GroupDto.Response();

        response.setGroupId( group.getGroupId() );
        response.setGroupName( group.getGroupName() );
        response.setNickname( group.getNickname() );
        response.setNowPersonnel( group.getNowPersonnel() );
        response.setContent( group.getContent() );

        return response;
    }
}
