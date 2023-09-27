package com.dmuIt.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND,"팀을 찾을 수 없습니다."),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "Study not found"),
    CLASS_CHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "반 변경 게시글을 찾을 수 없습니다."),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "Group not found"),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Notice not found"),
    CONVERSATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Conversation not found"),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Application not found"),
    MEMBER_EXISTS(409, "Member exists"),
    NICKNAME_EXISTS(409, "Nickname exists"),
    GROUP_IS_FULL(409, "Group is full"),
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
