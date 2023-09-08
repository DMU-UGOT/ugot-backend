package com.dmuIt.domain.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommunityResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private Long voteCount;
    private Long commentCount;
    private LocalDateTime created_at;
    private String nickname;
}
