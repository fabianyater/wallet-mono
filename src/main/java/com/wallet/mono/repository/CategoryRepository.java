package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c where c.categoryName = ?1")
    Category findTaxCategory(String categoryName);
}
