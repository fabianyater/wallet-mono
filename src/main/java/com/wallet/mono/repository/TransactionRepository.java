package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccount_AccountId(Integer accountId);
}
