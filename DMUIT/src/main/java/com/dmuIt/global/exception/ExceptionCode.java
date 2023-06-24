package com.dmuIt.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    MEMBER_EXISTS(409, "Member exists"),
    NICKNAME_EXISTS(409, "Nickname exists"),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND,"팀을 찾을 수 없습니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    @Getter
    private int status;
    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
