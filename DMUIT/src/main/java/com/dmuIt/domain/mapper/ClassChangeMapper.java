package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.ClassChangeDto;
import com.dmuIt.domain.entity.ClassChange;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClassChangeMapper {
    ClassChange classChangePostDtoToClassChange(ClassChangeDto.Post classChangePostDto);
    ClassChange classChangePatchDtoToClassChange(ClassChangeDto.Patch classChangePatchDto);

    default ClassChangeDto.Response classChangeToClassChangeResponseDto(ClassChange classChange) {
        if (classChange == null) {
            return null;
        } else {
            ClassChangeDto.Response response = new ClassChangeDto.Response();

            response.setClassChangeId(classChange.getClassChangeId());
            response.setGrade(classChange.getGrade());
            response.setCreatedAt(classChange.getCreatedAt());
            response.setNickname(classChange.getMember().getNickname());
            response.setCurrentClass(classChange.getCurrentClass());
            response.setChangeClass(classChange.getChangeClass());
            response.setStatus(classChange.getStatus());
            response.setMemberId(classChange.getMember().getMemberId());

            return response;
        }
    }
    List<ClassChangeDto.Response> classChangesToClassChangeResponseDtos(List<ClassChange> classChanges);
}
