package com.dmuIt.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode implements ErrorCode{
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment not found"),
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Community not found"),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND,"Team not found"),
    STUDY_NOT_FOUND(HttpStatus.NOT_FOUND, "Study not found"),
    CLASS_CHANGE_NOT_FOUND(HttpStatus.NOT_FOUND, "ClassChange not found"),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "Group not found"),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "Notice not found"),
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Message not found"),
    CONVERSATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Conversation not found"),
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Application not found"),
    MEMBER_EXISTS(HttpStatus.CONFLICT, "Member exists"),
    NICKNAME_EXISTS(HttpStatus.CONFLICT, "Nickname exists"),
    APPLICATION_EXISTS(HttpStatus.CONFLICT, "You've already applied for this group"),
    GROUP_IS_FULL(HttpStatus.CONFLICT, "Group is full"),
    NO_PERMISSION(HttpStatus.FORBIDDEN, "No Permission");

    private final HttpStatus httpStatus;
    private final String message;
//
//    ExceptionCode(int code, String message) {
//        this.status = code;
//        this.message = message;
//    }
//    ExceptionCode(HttpStatus httpStatus, String message) {
//        this.httpStatus = httpStatus;
//        this.message = message;
//    }
}
