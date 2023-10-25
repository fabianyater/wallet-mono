package com.wallet.mono.service;

import com.wallet.mono.domain.dto.CategoryRequest;
import com.wallet.mono.domain.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getCategories();
    CategoryResponse getTaxCategory();
}
