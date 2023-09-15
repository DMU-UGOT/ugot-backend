package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.ClassChangeDto;
import com.dmuIt.domain.entity.ClassChange;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-15T19:37:35+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ClassChangeMapperImpl implements ClassChangeMapper {

    @Override
    public ClassChange classChangePostDtoToClassChange(ClassChangeDto.Post classChangePostDto) {
        if ( classChangePostDto == null ) {
            return null;
        }

        ClassChange classChange = new ClassChange();

        classChange.setGrade( classChangePostDto.getGrade() );
        classChange.setCurrentClass( classChangePostDto.getCurrentClass() );
        classChange.setChangeClass( classChangePostDto.getChangeClass() );

        return classChange;
    }

    @Override
    public ClassChange classChangePatchDtoToClassChange(ClassChangeDto.Patch classChangePatchDto) {
        if ( classChangePatchDto == null ) {
            return null;
        }

        ClassChange classChange = new ClassChange();

        classChange.setGrade( classChangePatchDto.getGrade() );
        classChange.setCurrentClass( classChangePatchDto.getCurrentClass() );
        classChange.setChangeClass( classChangePatchDto.getChangeClass() );
        classChange.setStatus( classChangePatchDto.getStatus() );

        return classChange;
    }

    @Override
    public List<ClassChangeDto.Response> classChangesToClassChangeResponseDtos(List<ClassChange> classChanges) {
        if ( classChanges == null ) {
            return null;
        }

        List<ClassChangeDto.Response> list = new ArrayList<ClassChangeDto.Response>( classChanges.size() );
        for ( ClassChange classChange : classChanges ) {
            list.add( classChangeToClassChangeResponseDto( classChange ) );
        }

        return list;
    }
}
