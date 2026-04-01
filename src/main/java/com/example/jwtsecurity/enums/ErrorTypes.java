package com.example.jwtsecurity.enums;

import lombok.Getter;

@Getter
public enum ErrorTypes {
    INVALID_INPUT("Invalid input"),
    USER_ALREADY_EXIST("ISTIFADECI ARTIQ MOVCUDDUR"),
    NOT_FOUND("Not found"),
    BUSINESS_ERROR("Business rule violation"),
    SERVER_ERROR("Internal server error"),
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    USER_NOT_FOUND("User not found");

    private final String message;

    ErrorTypes(String message) {
        this.message = message;
    }
}
