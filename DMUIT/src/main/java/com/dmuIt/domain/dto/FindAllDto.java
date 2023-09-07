package com.dmuIt.domain.dto;

import lombok.Getter;

@Getter
public class FindAllDto<T> {
    private T data;
    private PageInfo pageInfo;

    public FindAllDto(T data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
