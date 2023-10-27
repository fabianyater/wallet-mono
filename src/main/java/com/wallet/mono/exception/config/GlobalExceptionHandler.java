package com.wallet.mono.exception.config;

import com.wallet.mono.exception.*;
import com.wallet.mono.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNameAlreadyExistsException(
            UserNameAlreadyExistsException ignoredNoDataFoundException) {
        ApiResponse<Object> errorResponse = new ApiResponse<>();
        errorResponse.setMessage(ExceptionResponse.USER_NAME_ALREADY_EXISTS.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAccountAlreadyExistsException(
            AccountAlreadyExistsException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ACCOUNT_ALREADY_EXISTS.getMessage()));
    }

    @ExceptionHandler(CategoryNotSelectedException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotSelectedException(
            CategoryNotSelectedException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.CATEGORY_NOT_SELECTED.getMessage()));
    }

    @ExceptionHandler(TypeNotSelectedException.class)
    public ResponseEntity<Map<String, String>> handleTypeNotSelectedException(
            TypeNotSelectedException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TYPE_NOT_SELECTED.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientBalanceException(
            InsufficientBalanceException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.INSUFFICIENT_BALANCE.getMessage()));
    }

    @ExceptionHandler(CustomArithmeticException.class)
    public ResponseEntity<Map<String, String>> handleCustomArithmeticException(
            CustomArithmeticException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.OPERATION_NOT_ALLOWED.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundExistsException(
            UserNotFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFoundException(
            AccountNotFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.ACCOUNT_NOT_FOUND.getMessage()));
    }

    @ExceptionHandler(TransactionDoesNotExists.class)
    public ResponseEntity<Map<String, String>> handleTransactionDoesNotExists(
            TransactionDoesNotExists ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.TRANSACTION_DOES_NOT_EXISTS.getMessage()));
    }

    @ExceptionHandler(TransactionError.class)
    public ResponseEntity<ApiResponse<Object>> handleTransactionError(
            TransactionError ignoredNoDataFoundException) {
        ApiResponse<Object> errorResponse = new ApiResponse<>();
        errorResponse.setMessage(ExceptionResponse.UNABLE_TO_DELETE_TRANSACTIONS.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

}
