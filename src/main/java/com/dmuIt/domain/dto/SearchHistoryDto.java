package com.dmuIt.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchHistoryDto {
    private String keyword;
}
