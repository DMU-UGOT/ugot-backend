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
            study.setAllPersonnel(studyPatchDto.getAllPersonnel());
            study.setKakaoOpenLink(studyPatchDto.getKakaoOpenLink());
            study.setGitHubLink(studyPatchDto.getGitHubLink());
            return study;
        }
    }
    StudyDto.Response studyToResponse(Study study);
    List<StudyDto.Response> studiesToStudyResponseDtos(List<Study> studies);
}
