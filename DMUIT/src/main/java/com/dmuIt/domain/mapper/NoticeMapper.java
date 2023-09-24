package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.NoticeDto;
import com.dmuIt.domain.entity.Notice;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoticeMapper {
    default NoticeDto.Response noticeToResponse(Notice notice) {
        if (notice == null) {
            return null;
        } else {
            NoticeDto.Response response = new NoticeDto.Response();
            response.setNoticeId(notice.getNoticeId());
            response.setDate(notice.getDate());
            response.setContent(notice.getContent());

            return response;
        }
    }
    List<NoticeDto.Response> NoticesToNoticeResponseDtos(List<Notice> notices);
}
