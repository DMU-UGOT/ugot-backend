package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.StudyDto;
import com.dmuIt.domain.entity.Study;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-08T17:59:58+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Oracle Corporation)"
)
@Component
public class StudyMapperImpl implements StudyMapper {

    @Override
    public Study studyPostDtoToStudy(StudyDto.Post studyPostDto) {
        if ( studyPostDto == null ) {
            return null;
        }

        Study study = new Study();

        study.setTitle( studyPostDto.getTitle() );
        study.setContent( studyPostDto.getContent() );
        study.setIsContact( studyPostDto.getIsContact() );
        study.setAllPersonnel( studyPostDto.getAllPersonnel() );
        study.setGitHubLink( studyPostDto.getGitHubLink() );

        return study;
    }

    @Override
    public List<StudyDto.Response> studiesToStudyResponseDtos(List<Study> studies) {
        if ( studies == null ) {
            return null;
        }

        List<StudyDto.Response> list = new ArrayList<StudyDto.Response>( studies.size() );
        for ( Study study : studies ) {
            list.add( studyToResponse( study ) );
        }

        return list;
    }
}
