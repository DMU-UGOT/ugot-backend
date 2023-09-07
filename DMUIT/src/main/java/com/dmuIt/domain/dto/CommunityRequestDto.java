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
    private char deleteYN;

    public Community toEntity() {
        return Community.builder()
                .title(title)
                .content(content)
                .viewCount(0)
                .deleteYN(deleteYN)
                .build();
    }
}
