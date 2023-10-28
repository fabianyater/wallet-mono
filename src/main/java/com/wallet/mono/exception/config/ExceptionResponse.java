package com.wallet.mono.exception.config;

public enum ExceptionResponse {
    USER_NAME_ALREADY_EXISTS("Nombre de usuario ya existe"),
    ACCOUNT_ALREADY_EXISTS("Esta cuenta ya existe"),
    USER_NOT_FOUND("Usuario no encontrado"),
    ACCOUNT_NOT_FOUND("Cuenta no encontrada"),
    CATEGORY_NOT_SELECTED("Categoría no seleccionada"),
    CATEGORY_ALREADY_EXISTS("Nombre de categoría ya existe"),
    CATEGORY_DOES_NOT_EXIXTS("Categoría no existe"),
    DEFAULT_CATEGORY("Esta categoría por defecto no se puede modificar"),
    TYPE_NOT_SELECTED("Tipo de movimiento no seleccionado"),
    INSUFFICIENT_BALANCE("Saldo insuficiente"),
    TRANSACTION_DOES_NOT_EXISTS("Este movimiento no existe"),
    OPERATION_NOT_ALLOWED("Operación no permitida"),
    UNABLE_TO_DELETE_TRANSACTIONS("No es posible eliminar las transacciones");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

