package com.dmuIt.domain.mapper;

import com.dmuIt.domain.entity.Community;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-07T19:47:13+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class CommunityMapperImpl implements CommunityMapper {

    @Override
    public List<Community> ComsToComResponseDtos(List<Community> Community) {
        if ( Community == null ) {
            return null;
        }

        List<Community> list = new ArrayList<Community>( Community.size() );
        for ( Community community : Community ) {
            list.add( community );
        }

        return list;
    }
}
