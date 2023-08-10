package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Community;

public class CommunityRequestDto {
    private String title; // 제목
    private String content; // 내용
    private Long member_id; // 작성자
    private char deleteYN; // 삭제 여부

    public Community toEntity() {
        return Community.builder()
                .title(title)
                .content(content)
                .member_id(member_id)
                .viewCount(0)
                .deleteYN(deleteYN)
                .build();
    }
}
