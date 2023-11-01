package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryIdAndUser_UserId(Integer categoryId, Integer userId);
    @Query("select c from Category c where c.isDefault is true")
    List<Category> findDefaultCategories();
    List<Category> findByUser_UserId(Integer userId);
    @Query("select c from Category c where c.categoryName = ?1")
    Category findTaxCategory(String categoryName);
}
