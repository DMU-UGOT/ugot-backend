package com.dmuIt.domain.mapper;

import com.dmuIt.domain.dto.NoticeDto;
import com.dmuIt.domain.entity.Notice;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-26T21:00:07+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class NoticeMapperImpl implements NoticeMapper {

    @Override
    public List<NoticeDto.Response> NoticesToNoticeResponseDtos(List<Notice> notices) {
        if ( notices == null ) {
            return null;
        }

        List<NoticeDto.Response> list = new ArrayList<NoticeDto.Response>( notices.size() );
        for ( Notice notice : notices ) {
            list.add( noticeToResponse( notice ) );
        }

        return list;
    }
}
