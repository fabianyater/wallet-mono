package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper {
    CategoryResponse mapToCategoryResponse(Category category);
    Category mapToCategory(CategoryResponse categoryResponse);

    List<CategoryResponse> mapToCategoryResponseList(List<Category> categories);
}
