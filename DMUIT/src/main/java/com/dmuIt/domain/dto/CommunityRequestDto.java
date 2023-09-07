package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Community;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityRequestDto {
    private String title;
    private String content;
    private Long member_id;

    public Community toEntity() {
        return Community.builder()
                .title(title)
                .content(content)
                .member_id(member_id)
                .viewCount(0)
                .build();
    }
}
