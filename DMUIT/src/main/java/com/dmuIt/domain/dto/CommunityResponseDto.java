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
    private int viewCount;
    private int voteCount;
    private Long member_id;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;


    public CommunityResponseDto(Community entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.member_id = entity.getMember_id();
        this.created_at = entity.getCreatedAt();
        this.modified_at = entity.getModified_at();
        this.viewCount = (int) entity.getViewCount();
        this.voteCount = (int) entity.getVoteCount();
    }
}
