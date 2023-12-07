package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryIdAndUser_UserId(Integer categoryId, Integer userId);
    @Query("select c from Category c where c.isDefault is true")
    List<Category> findDefaultCategories();
    List<Category> findByUser_UserId(Integer userId);
    @Query("select c from Category c where c.categoryName = ?1")
    Category findTaxCategory(String categoryName);

    @Query(value = "SELECT c.category_name, SUM(t.transaction_amount) AS total_income, c.color " +
            "FROM categories c " +
            "INNER JOIN transactions t ON c.id = t.category_id " +
            "WHERE t.transaction_type = :type " +
            "AND t.account_id = :accountId " +
            "AND t.transaction_date >= CAST(:startDate AS timestamp) " +
            "AND t.transaction_date < CAST(:endDate AS timestamp) " +
            "GROUP BY c.category_name, c.color " +
            "ORDER BY total_income DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findWeeklyCategorySummary(@Param("accountId") int accountId, @Param("type") String type, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
