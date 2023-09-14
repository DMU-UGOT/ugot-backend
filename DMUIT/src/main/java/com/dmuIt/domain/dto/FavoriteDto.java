package com.dmuIt.domain.dto;

import com.dmuIt.domain.entity.Favorite;
import com.dmuIt.domain.entity.Group;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteDto {

    private Long memberId;
    private Long groupId;

    public FavoriteDto(Favorite entity) {
        this.memberId = entity.getMember().getMemberId();
        this.groupId = entity.getGroup().getId();
    }
}