package com.dmuIt.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    MEMBER_EXISTS(409, "Member exists"),
    NICKNAME_EXISTS(409, "Nickname exists"),
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND,"팀을 찾을 수 없습니다."),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "Study not found"),
    CLASS_CHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "반 변경 게시글을 찾을 수 없습니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다.");

    private int status;
    private HttpStatus httpStatus;

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
