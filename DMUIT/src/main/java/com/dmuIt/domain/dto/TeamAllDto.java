package com.dmuIt.domain.dto;

import lombok.Getter;

@Getter
public class TeamAllDto<T> {
    private T data;
    private PageInfo pageInfo;

    public TeamAllDto(T data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
