package com.dmuIt.domain.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private Long voteCount;
    private LocalDateTime created_at;
}
