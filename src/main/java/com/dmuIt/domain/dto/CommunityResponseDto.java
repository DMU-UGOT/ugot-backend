package com.dmuIt.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommunityResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private Long commentCount;
    private LocalDateTime created_at;
    private String nickname;
    private Long memberId;

    public CommunityResponseDto(Long id, String title, String content,
                                Long viewCount, Long commentCount, LocalDateTime created_at,
                                String nickname, Long memberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.created_at = created_at;
        this.nickname = nickname;
        this.memberId = memberId;
    }
}
