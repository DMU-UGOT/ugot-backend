package com.dmuIt.domain.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteDto {

    private Long memberId;
    private Long groupId;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private Long groupId;
    }
}