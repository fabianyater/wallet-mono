package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findByTransactionIdAndAccount_AccountId(Integer transactionId, Integer accountId);

    boolean existsByTransactionId(Integer transactionId);

    Page<Transaction> findByAccount_AccountId(Integer accountId, Pageable pageable);

    long countByAccount_AccountId(Integer accountId);

    @Query(value = "select SUM(t.transaction_amount) \n" +
            "from transactions t \n" +
            "inner join categories c \n" +
            "on t.category_id = c.id \n" +
            "inner join accounts a \n" +
            "on t.account_id = a.id \n" +
            "where a.id = :accountId and t.transaction_type = 'expense' and c.category_name <> 'tax'", nativeQuery = true)
    Double getTransactionsAmountByAccountId(@Param("accountId") Integer accountId);

    @Query(value = "select sum(t.transaction_amount) from transactions t \n" +
            "inner join accounts a \n" +
            "on t.account_id = a.id \n" +
            "where a.id = :accountId\n" +
            "and extract(YEAR from t.transaction_date) = :year \n " +
            "and extract(MONTH from t.transaction_date) = :month \n " +
            "group by t.transaction_type", nativeQuery = true)
    List<Double> getTotalTransactionAmountByAccountId(@Param("accountId") Integer accountId, @Param("year") Integer year, @Param("month") Integer month);

    @Query(value = "WITH days AS (" +
            "    SELECT date_trunc('day', serie) as date " +
            "    FROM generate_series(CAST(:startDate AS timestamp), CAST(:endDate AS timestamp), '1 day') serie " +
            ") " +
            "SELECT " +
            "    TO_CHAR(d.date, 'TMDy DD') as day_with_date, " +
            "    COALESCE(SUM(CASE WHEN t.transaction_type = 'income' THEN t.transaction_amount ELSE 0 END), 0) as totalIncome, " +
            "    COALESCE(SUM(CASE WHEN t.transaction_type = 'expense' THEN t.transaction_amount ELSE 0 END), 0) as totalExpense " +
            "FROM days d " +
            "LEFT JOIN transactions t ON date_trunc('day', t.transaction_date) = d.date " +
            "AND t.transaction_date >= CAST(:startDate AS timestamp) " +
            "AND t.transaction_date < CAST(:endDate AS timestamp) " +
            "AND t.account_id = :accountId " +
            "AND (t.transaction_type = 'income' OR t.transaction_type = 'expense') " +
            "GROUP BY d.date " +
            "ORDER BY d.date", nativeQuery = true)
    List<Object[]> findWeeklyTransactionSummary(@Param("accountId") int accountId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "WITH date_series AS ( " +
            "    SELECT date_trunc('day', series) as day " +
            "    FROM generate_series( " +
            "             DATE_TRUNC('month', TO_DATE(:year || '-' || :month || '-01', 'YYYY-MM-DD')), " +
            "             DATE_TRUNC('month', TO_DATE(:year || '-' || :month || '-01', 'YYYY-MM-DD')) + INTERVAL '1 month - 1 day', " +
            "             '1 day' " +
            "         ) series " +
            ") " +
            "SELECT TO_CHAR(ds.day, 'TMDy DD') as transactionDay, " +
            "       COALESCE(SUM(CASE WHEN t.transaction_type = 'income' THEN t.transaction_amount ELSE 0 END), 0) as totalIncome, " +
            "       COALESCE(SUM(CASE WHEN t.transaction_type = 'expense' THEN t.transaction_amount ELSE 0 END), 0) as totalExpense " +
            "FROM date_series ds " +
            "LEFT JOIN transactions t ON ds.day = DATE(t.transaction_date) AND t.account_id = :accountId " +
            "WHERE DATE_PART('year', ds.day) = :year AND DATE_PART('month', ds.day) = :month " +
            "GROUP BY ds.day " +
            "ORDER BY ds.day", nativeQuery = true)
    List<Object[]> findDailyTransactionsSummary(@Param("accountId") int accountId, @Param("year") int year, @Param("month") int month);

    @Query(value = "WITH month_series AS ( " +
            "    SELECT DATE_TRUNC('month', series) as month " +
            "    FROM generate_series( " +
            "             DATE_TRUNC('year', TO_DATE(:year || '-01-01', 'YYYY-MM-DD')), " +
            "             DATE_TRUNC('year', TO_DATE(:year || '-01-01', 'YYYY-MM-DD')) + INTERVAL '1 year - 1 day', " +
            "             '1 month' " +
            "         ) series " +
            ") " +
            "SELECT TO_CHAR(ms.month, 'TMMon') as transactionMonth, " +
            "       COALESCE(SUM(CASE WHEN t.transaction_type = 'income' THEN t.transaction_amount ELSE 0 END), 0) as totalIncome, " +
            "       COALESCE(SUM(CASE WHEN t.transaction_type = 'expense' THEN t.transaction_amount ELSE 0 END), 0) as totalExpense " +
            "FROM month_series ms " +
            "LEFT JOIN transactions t ON DATE_TRUNC('month', t.transaction_date) = ms.month AND t.account_id = :accountId " +
            "WHERE EXTRACT(YEAR FROM ms.month) = :year " +
            "GROUP BY ms.month " +
            "ORDER BY ms.month", nativeQuery = true)
    List<Object[]> findMonthlyTransactionsSummaryForYear(@Param("accountId") int accountId, @Param("year") int year);


}
