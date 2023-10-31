package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
    private int categoryId;
    private String categoryName;
    private String type;
    private boolean defaultCategory;
    private int userId;
}
