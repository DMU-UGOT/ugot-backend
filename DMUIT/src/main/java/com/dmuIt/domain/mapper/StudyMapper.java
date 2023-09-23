package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.StudyDto;
import com.dmuIt.domain.entity.Study;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudyMapper {
    Study studyPostDtoToStudy(StudyDto.Post studyPostDto);
    default Study studyPatchDtoToStudy(StudyDto.Patch studyPatchDto) {
        if (studyPatchDto == null) {
            return null;
        } else {
            Study study = new Study();
            study.setTitle(studyPatchDto.getTitle());
            study.setContent(studyPatchDto.getContent());
            study.setIsContact(studyPatchDto.getIsContact());
            study.setSubject(studyPatchDto.getSubject());
            study.setField(studyPatchDto.getField());
            study.setKakaoOpenLink(studyPatchDto.getKakaoOpenLink());
            study.setGitHubLink(studyPatchDto.getGitHubLink());
            return study;
        }
    }

    default StudyDto.Response studyToResponse(Study study) {
        if (study == null) {
            return null;
        } else {
            StudyDto.Response response = new StudyDto.Response();
            if (study.getStudyId() != null) {
                response.setStudyId(study.getStudyId());
            }
            response.setStudyId( study.getStudyId() );
            response.setTitle( study.getTitle() );
            response.setContent( study.getContent() );
            response.setIsContact( study.getIsContact() );
            response.setNickname(study.getMember().getNickname());
            response.setBookmarked(study.getBookmarked());
            response.setAllPersonnel( study.getGroup().getAllPersonnel() );
            response.setNowPersonnel( study.getGroup().getNowPersonnel());
            response.setSubject(study.getSubject());
            response.setField(study.getField());
            response.setKakaoOpenLink( study.getKakaoOpenLink() );
            response.setGitHubLink( study.getGitHubLink() );
            response.setViewCount( study.getViewCount() );
            response.setCreatedAt( study.getCreatedAt() );
            return response;
        }
    }
    List<StudyDto.Response> studiesToStudyResponseDtos(List<Study> studies);
}
