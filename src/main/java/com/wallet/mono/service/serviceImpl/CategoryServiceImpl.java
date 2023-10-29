package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.CategoryDeleteRequest;
import com.wallet.mono.domain.dto.CategoryRequest;
import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.domain.mapper.CategoryRequestMapper;
import com.wallet.mono.domain.mapper.CategoryResponseMapper;
import com.wallet.mono.domain.model.Category;
import com.wallet.mono.exception.CategoryAlreadyDoesNotExists;
import com.wallet.mono.exception.CategoryAlreadyExists;
import com.wallet.mono.exception.DefatulCategory;
import com.wallet.mono.exception.TypeNotSelectedException;
import com.wallet.mono.repository.CategoryRepository;
import com.wallet.mono.service.CategoryService;
import com.wallet.mono.utils.DefaultCategories;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.wallet.mono.utils.DefaultCategories.DEFAULT_CATEGORIES_MAP;

@AllArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CategoryRequestMapper categoryRequestMapper;

    private void loadCategories() {
        List<Category> categoriesToSave = new ArrayList<>();
        for (Map.Entry<String, DefaultCategories.CategoryType> entry : DEFAULT_CATEGORIES_MAP.entrySet()) {
            Category category = new Category();
            category.setCategoryName(entry.getKey());
            category.setType(entry.getValue().toString());

            categoriesToSave.add(category);
        }

        categoryRepository.saveAll(categoriesToSave);
    }

    @Override
    public void addCategory(CategoryRequest categoryRequest) throws CategoryAlreadyExists, DefatulCategory, TypeNotSelectedException {
        if (categoryRequest.getType() == null) {
            throw new TypeNotSelectedException();
        }

        if (DEFAULT_CATEGORIES_MAP.containsKey(categoryRequest.getCategoryName())) {
            throw new DefatulCategory();
        }

        List<Category> categories = categoryRepository.findAll();

        for (Category existingCategory : categories) {
            if (existingCategory.getCategoryName().equals(categoryRequest.getCategoryName())) {
                throw new CategoryAlreadyExists();
            }
        }

        Category category = categoryRequestMapper.mapToCategory(categoryRequest);
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getCategories(int userId) {
        List<Category> defaultCategories = categoryRepository.findDefaultCategories();
        List<Category> categories = categoryRepository.findByUser_UserId(userId);
        List<Category> response = new ArrayList<>(defaultCategories);
        response.addAll(categories);

        return categoryResponseMapper.mapToCategoryResponseList(response);
    }

    @Override
    public CategoryResponse getTaxCategory() {
        Category category = categoryRepository.findTaxCategory("Tax");
        return categoryResponseMapper.mapToCategoryResponse(category);
    }

    @Override
    public Optional<CategoryResponse> getCategory(int categoryId, int userId) throws CategoryAlreadyDoesNotExists {
        return Optional.ofNullable(Optional.ofNullable(categoryResponseMapper
                        .mapToCategoryResponse(categoryRepository
                                .findByCategoryIdAndUser_UserId(categoryId, userId)))
                .orElseThrow(CategoryAlreadyDoesNotExists::new));
    }

    @Override
    public void updateCategory(int categoryId, CategoryRequest categoryRequest) throws Exception {
        Optional<CategoryResponse> oldCategory = getCategory(categoryId, categoryRequest.getUserId());

        if (oldCategory.isEmpty()) {
            throw new CategoryAlreadyDoesNotExists();
        }

        oldCategory.get().setCategoryName(categoryRequest.getCategoryName());
        oldCategory.get().setType(categoryRequest.getType());

        Category newCategory = categoryResponseMapper.mapToCategory(oldCategory.get());

        categoryRepository.save(newCategory);
    }

    @Override
    public void deleteCategory(CategoryDeleteRequest categoryDeleteRequest) throws CategoryAlreadyDoesNotExists {
        int categoryId = categoryDeleteRequest.getCategoryId();
        int userId = categoryDeleteRequest.getUserId();

        getCategory(categoryId, userId);

        categoryRepository.deleteById(categoryId);
    }
}
