package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.*;
import com.wallet.mono.domain.dto.request.CategoryDeleteRequest;
import com.wallet.mono.domain.dto.request.CategoryRequest;
import com.wallet.mono.domain.dto.response.AdditionalInfoResponse;
import com.wallet.mono.domain.dto.response.CategoryResponse;
import com.wallet.mono.domain.mapper.CategoryRequestMapper;
import com.wallet.mono.domain.mapper.CategoryResponseMapper;
import com.wallet.mono.domain.model.Category;
import com.wallet.mono.exception.CategoryAlreadyDoesNotExists;
import com.wallet.mono.exception.CategoryAlreadyExists;
import com.wallet.mono.exception.DefatulCategory;
import com.wallet.mono.exception.TypeNotSelectedException;
import com.wallet.mono.repository.CategoryRepository;
import com.wallet.mono.service.CategoryService;
import com.wallet.mono.utils.AdditionalInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.wallet.mono.utils.DefaultCategories.DEFAULT_CATEGORIES_MAP;

@AllArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryResponseMapper categoryResponseMapper;
    private final CategoryRequestMapper categoryRequestMapper;

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
        category.setIsDefault(false);
        categoryRepository.save(category);
    }

    @Override
    public AdditionalInfoResponse getCategories(int userId) {
        List<Category> defaultCategories = categoryRepository.findDefaultCategories();
        List<Category> categories = categoryRepository.findByUser_UserId(userId);
        List<Category> response = new ArrayList<>(defaultCategories);
        response.addAll(categories);

        long totalIncomeCategories = response.stream()
                .filter(category -> category.getType().equals("INCOME") && category.getIsDefault())
                .count();

        long totalExpenseCategories = response.stream()
                .filter(category -> category.getType().equals("EXPENSE") && category.getIsDefault())
                .count();

        long totalIncomeUserCategories = categories.stream()
                .filter(category -> category.getType().equals("INCOME") && category.getUser().getUserId().equals(userId))
                .count();

        long totalExpenseUserCategories = categories.stream()
                .filter(category -> category.getType().equals("EXPENSE") && category.getUser().getUserId().equals(userId))
                .count();

        List<CategoryResponse> categoryResponses = categoryResponseMapper.mapToCategoryResponseList(response);
        AdditionalInfo additionalInfo = new AdditionalInfo();
        additionalInfo.setIncomeDefaultCategories((int) totalIncomeCategories);
        additionalInfo.setExpenseDefaultCategories((int) totalExpenseCategories);
        additionalInfo.setIncomeUserCategories((int) totalIncomeUserCategories);
        additionalInfo.setExpenseUserCategories((int) totalExpenseUserCategories);
        additionalInfo.setTotalIncome((int) totalIncomeCategories + (int) totalIncomeUserCategories);
        additionalInfo.setTotalExpense((int) totalExpenseCategories + (int) totalExpenseUserCategories);

        AdditionalInfoResponse infoResponse = new AdditionalInfoResponse();
        infoResponse.setCategoryResponse(categoryResponses);
        infoResponse.setAdditionalInfo(additionalInfo);

        return infoResponse;
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
        oldCategory.get().setUserId(categoryRequest.getUserId());

        Category newCategory = categoryResponseMapper.mapToCategory(oldCategory.get());

        categoryRepository.save(newCategory);
    }

    @Override
    public void deleteCategory(CategoryDeleteRequest categoryDeleteRequest) throws CategoryAlreadyDoesNotExists, DefatulCategory {

        int categoryId = categoryDeleteRequest.getCategoryId();
        int userId = categoryDeleteRequest.getUserId();

        Optional<CategoryResponse> categoryResponse = getCategory(categoryId, userId);

        if (categoryResponse.isPresent()) {
            if (DEFAULT_CATEGORIES_MAP.containsKey(categoryResponse.get().getCategoryName())) {
                throw new DefatulCategory();
            }

            categoryRepository.deleteById(categoryId);
        }

    }

    @Override
    public CategoryStatistics getCategorySummary(int accountId, String transactionType, Date startDate, Date endDate) {
        List<Object[]> list = categoryRepository.findWeeklyCategorySummary(accountId, transactionType, startDate, endDate);
        Double total = 0.0;

        for (Object[] objects : list) {
            if (objects[1] != null) {
                total += (Double) objects[1];
            }
        }

        List<CategorySummary> categorySummary = categoryResponseMapper.mapToCategorySummaryList(list);

        CategoryStatistics categoryStatistics = new CategoryStatistics();
        categoryStatistics.setCategorySummary(categorySummary);
        categoryStatistics.setTotal(total);

        return categoryStatistics;
    }

    @Override
    public CategoryStatistics getMonthlyCategorySummary(int accountId, String transactionType, int year, int month) {
        List<Object[]> list = categoryRepository.findMonthlySummaryByTypeYearAndMonth(accountId, transactionType, year, month);
        Double total = 0.0;

        for (Object[] objects : list) {
            if (objects[1] != null) {
                total += (Double) objects[1];
            }
        }

        List<CategorySummary> categorySummary = categoryResponseMapper.mapToCategorySummaryList(list);

        CategoryStatistics categoryStatistics = new CategoryStatistics();
        categoryStatistics.setCategorySummary(categorySummary);
        categoryStatistics.setTotal(total);

        return categoryStatistics;
    }

    @Override
    public CategoryStatistics getAnnualCategorySummary(int accountId, String transactionType, int year) {
        List<Object[]> list = categoryRepository.findAnnualSummaryByTypeAndYear(accountId, transactionType, year);
        Double total = 0.0;

        for (Object[] objects : list) {
            if (objects[1] != null) {
                total += (Double) objects[1];
            }
        }

        List<CategorySummary> categorySummary = categoryResponseMapper.mapToCategorySummaryList(list);

        CategoryStatistics categoryStatistics = new CategoryStatistics();
        categoryStatistics.setCategorySummary(categorySummary);
        categoryStatistics.setTotal(total);

        return categoryStatistics;
    }
}
