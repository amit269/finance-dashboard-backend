package com.finance.dashboard.user.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    EMAIL_NOT_FOUND     ("1000", "Email is not provided"),
    USER_NOT_CREATED    ("1001", "User could not be created"),
    USER_NOT_FOUND      ("1002", "User not found"),
    USER_ALREADY_EXISTS ("1003", "User with this email already exists"),
    ACCESS_DENIED       ("1004", "You do not have permission to perform this action"),
    GENERIC_ERROR       ("1005", "An unexpected error occurred. Please try again later");

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}