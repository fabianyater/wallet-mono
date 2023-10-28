package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.CategoryRequest;
import com.wallet.mono.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {

    @Mapping(target = "userId", source = "user.userId")
    CategoryRequest mapToCategoryRequest(Category category);

    @Mapping(target = "user.userId", source = "userId")
    Category mapToCategory(CategoryRequest categoryRequest);
}
