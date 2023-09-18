package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteDto {

    private Long groupId;
    private Long likeId;
    private String groupName;

    public FavoriteDto(Favorite entity) {
        this.likeId = entity.getLikeId();
        this.groupName = entity.getGName();
        this.groupId = entity.getGId();
    }
    @Getter
    @Setter
    public static class Response {
        private Long groupId;
        private String groupName;
    }
}