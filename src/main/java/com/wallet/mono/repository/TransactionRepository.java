package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            "group by t.transaction_type", nativeQuery = true)
    List<Double> getTotalTransactionAmountByAccountId(@Param("accountId") Integer accountId);
}
