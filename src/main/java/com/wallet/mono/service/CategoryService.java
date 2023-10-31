package com.wallet.mono.service;

import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.dto.CategoryDeleteRequest;
import com.wallet.mono.domain.dto.CategoryRequest;
import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.exception.CategoryAlreadyDoesNotExists;
import com.wallet.mono.exception.CategoryAlreadyExists;
import com.wallet.mono.exception.DefatulCategory;
import com.wallet.mono.exception.TypeNotSelectedException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void addCategory(CategoryRequest categoryRequest) throws CategoryAlreadyExists, DefatulCategory, TypeNotSelectedException;
    List<CategoryResponse> getCategories(int userId);

    CategoryResponse getTaxCategory();
    Optional<CategoryResponse> getCategory(int categoryId, int userId) throws CategoryAlreadyDoesNotExists;
    void updateCategory(int categoryId, CategoryRequest categoryRequest) throws Exception;
    void deleteCategory(CategoryDeleteRequest categoryDeleteRequest) throws CategoryAlreadyDoesNotExists, DefatulCategory;
}
