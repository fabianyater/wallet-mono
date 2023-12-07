package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.domain.dto.CategorySummary;
import com.wallet.mono.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper {
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "defaultCategory", source = "isDefault")
    CategoryResponse mapToCategoryResponse(Category category);

    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "isDefault", source = "defaultCategory")
    Category mapToCategory(CategoryResponse categoryResponse);
    List<CategoryResponse> mapToCategoryResponseList(List<Category> categories);

    default CategorySummary mapToCategorySummary(Object[] object) {
        CategorySummary categorySummary = new CategorySummary();
        categorySummary.setCategoryName((String) object[0] );
        categorySummary.setValue((double) object[1] );
        categorySummary.setColor((String) object[2]);

        return categorySummary;
    }

    default List<CategorySummary> mapToCategorySummaryList(List<Object[]> objects) {
        if (objects == null) {
            return Collections.emptyList();
        }

        return objects.stream()
                .map(this::mapToCategorySummary)
                .collect(Collectors.toList());
    }
}
