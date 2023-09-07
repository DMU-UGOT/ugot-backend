package com.dmuIt.domain.dto;

import lombok.Getter;

@Getter
<<<<<<<< HEAD:DMUIT/src/main/java/com/dmuIt/domain/dto/PageDto.java
public class PageDto<P> {
    private P data;
    private PageInfo pageInfo;

    public PageDto(P data, PageInfo pageInfo) {
========
public class FindAllDto<T> {
    private T data;
    private PageInfo pageInfo;

    public FindAllDto(T data, PageInfo pageInfo) {
>>>>>>>> dev:DMUIT/src/main/java/com/dmuIt/domain/dto/FindAllDto.java
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
