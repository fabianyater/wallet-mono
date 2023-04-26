package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.CategoryResponse;
import com.wallet.mono.domain.mapper.CategoryResponseMapper;
import com.wallet.mono.domain.model.Category;
import com.wallet.mono.repository.CategoryRepository;
import com.wallet.mono.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categoryResponseMapper.mapToCategoryResponseList(categories);
    }
}
