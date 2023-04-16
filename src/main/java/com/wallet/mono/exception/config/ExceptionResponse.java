package com.wallet.mono.exception.config;

public enum ExceptionResponse {
    USER_NAME_ALREADY_EXISTS("User name already exists"),
    ACCOUNT_ALREADY_EXISTS("Account already exists"),
    USER_NOT_FOUND("User not found"),
    ACCOUNT_NOT_FOUND("Account not found");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

