package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findByTransactionIdAndAccount_AccountId(Integer transactionId, Integer accountId);
    boolean existsByTransactionId(Integer transactionId);
    Page<Transaction> findByAccount_AccountId(Integer accountId, Pageable pageable);
}
