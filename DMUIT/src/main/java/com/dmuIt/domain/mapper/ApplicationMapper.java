package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.ApplicationDto;
import com.dmuIt.domain.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {
    default ApplicationDto.Response applicationToResponse(Application application) {
        if (application == null) {
            return null;
        } else {
            ApplicationDto.Response response = new ApplicationDto.Response();
            response.setMemberId(application.getMember().getMemberId());
            response.setNickname(application.getMember().getNickname());
            response.setMajor(application.getMember().getMajor());
            response.setGrade(application.getMember().getGrade());
            response.set_class(application.getMember().get_class());
            response.setGitHubLink(application.getMember().getGitHubLink());
            response.setPersonalBlogLink(application.getMember().getPersonalBlogLink());
            response.setSkill(application.getMember().getSkill());
            return response;
        }
    }

    List<ApplicationDto.Response> applicationsToResponses(List<Application> applications);
}
