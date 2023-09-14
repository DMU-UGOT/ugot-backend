package com.dmuIt.domain.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteDto {

    private Long memberId;
    private Long groupId;

    public FavoriteDto(Long memberId, Long groupId) {
        this.memberId = memberId;
        this.groupId = groupId;
    }
}