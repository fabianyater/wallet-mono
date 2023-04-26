package com.wallet.mono.exception.config;

import com.wallet.mono.exception.AccountAlreadyExistsException;
import com.wallet.mono.exception.AccountNotFoundException;
import com.wallet.mono.exception.CategoryNotSelectedException;
import com.wallet.mono.exception.InsufficientBalanceException;
import com.wallet.mono.exception.TransactionDoesNotExists;
import com.wallet.mono.exception.TypeNotSelectedException;
import com.wallet.mono.exception.UserNameAlreadyExistsException;
import com.wallet.mono.exception.UserNotFoundException;
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
    public ResponseEntity<Map<String, String>> handleUserNameAlreadyExistsException(
            UserNameAlreadyExistsException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ExceptionResponse.USER_NAME_ALREADY_EXISTS.getMessage()));
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

}
