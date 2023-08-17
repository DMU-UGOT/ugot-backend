package com.dmuIt.domain.dto;
import com.dmuIt.domain.entity.Community;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class CommunityResponseDto {
    private Long id;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer voteCount;
    private Long member_id;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public CommunityResponseDto(Community entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.member_id = entity.getMember_id();
        this.status = entity.getStatus();
        this.created_at = entity.getCreated_at();
        this.modified_at = entity.getModified_at();
    }
}
