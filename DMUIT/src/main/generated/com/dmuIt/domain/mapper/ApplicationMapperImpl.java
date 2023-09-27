package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.ApplicationDto;
import com.dmuIt.domain.entity.Application;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-27T21:51:36+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class ApplicationMapperImpl implements ApplicationMapper {

    @Override
    public List<ApplicationDto.Response> applicationsToResponses(List<Application> applications) {
        if ( applications == null ) {
            return null;
        }

        List<ApplicationDto.Response> list = new ArrayList<ApplicationDto.Response>( applications.size() );
        for ( Application application : applications ) {
            list.add( applicationToResponse( application ) );
        }

        return list;
    }
}
