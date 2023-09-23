package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.NoticeDto;
import com.dmuIt.domain.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {
    List<NoticeDto.Response> NoticesToNoticeResponseDtos(List<Notice> notices);
}
