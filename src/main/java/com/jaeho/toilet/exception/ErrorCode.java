package com.jaeho.toilet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT,"Username is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"Password is invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"Token is invalid"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED,"permission is invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error"),
    ALREADY_LIKED(HttpStatus.CONFLICT,"User already liked the post");
    ;

    private HttpStatus status;
    private String message;
}
