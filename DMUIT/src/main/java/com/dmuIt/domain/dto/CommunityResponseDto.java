package com.dmuIt.domain.dto;
import lombok.Getter;
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
    private Date create_at;
    private Date modified_at;

    public CommunityResponseDto(Long id, String title, String content, Integer viewCount, Integer voteCount, Long member_id, String status, Date create_at, Date modified_at) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.voteCount = voteCount;
        this.member_id = member_id;
        this.status = status;
        this.create_at = create_at;
        this.modified_at = modified_at;
    }
}
