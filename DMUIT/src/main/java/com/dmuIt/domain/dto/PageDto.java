package com.dmuIt.domain.dto;

import lombok.Getter;

@Getter
public class PageDto<P> {
    private P data;
    private PageInfo pageInfo;

    public PageDto(P data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
