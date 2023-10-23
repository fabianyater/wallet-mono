package com.wallet.mono.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;
    private String message;
    private T data;
    private Pagination pagination;

    @Getter
    @Setter
    public static class Pagination {
        private int currentPage;
        private long totalPages;
        private Long totalItems;
        private int itemsPerPage;

        // Getters, Setters y Constructores
    }
}
