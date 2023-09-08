package com.dmuIt.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private LocalDateTime modified_at;
    private String nickname;

//    public CommunityResponseDto(Community entity) {
//        this.id = entity.getId();
//        this.title = entity.getTitle();
//        this.content = entity.getContent();
//        this.created_at = entity.getCreatedAt();
//        this.modified_at = entity.getModifiedAt();
//        this.viewCount = (int) entity.getViewCount();
//        this.voteCount = (int) entity.getVoteCount();
//    }
}
