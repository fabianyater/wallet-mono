package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryResponseMapper {
    @Mapping(target = "userId", source = "user.userId")
    CategoryResponse mapToCategoryResponse(Category category);

    @Mapping(target = "user.userId", source = "userId")
    Category mapToCategory(CategoryResponse categoryResponse);

    List<CategoryResponse> mapToCategoryResponseList(List<Category> categories);
}
