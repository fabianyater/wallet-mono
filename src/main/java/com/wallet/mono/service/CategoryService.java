package com.wallet.mono.service;

import com.wallet.mono.domain.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories();
}
