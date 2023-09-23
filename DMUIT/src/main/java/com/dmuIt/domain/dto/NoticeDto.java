package com.dmuIt.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class NoticeDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private int year;
        private int month;
        private int dateOfMonth;
        private String content;
    }



    @Getter
    @Setter
    public static class Response {
        private LocalDate date;
        private String content;
    }
}
