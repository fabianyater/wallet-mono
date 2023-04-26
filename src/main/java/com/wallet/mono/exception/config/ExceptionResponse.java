package com.wallet.mono.exception.config;

public enum ExceptionResponse {
    USER_NAME_ALREADY_EXISTS("User name already exists"),
    ACCOUNT_ALREADY_EXISTS("Account already exists"),
    USER_NOT_FOUND("User not found"),
    ACCOUNT_NOT_FOUND("Account not found"),
    CATEGORY_NOT_SELECTED("Category not selected"),
    TYPE_NOT_SELECTED("Type not selected"),
    INSUFFICIENT_BALANCE("Insufficient balance"),
    TRANSACTION_DOES_NOT_EXISTS("Transaction does not exists");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

