package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUser_UserId(Integer userId);
    boolean existsByAccountName(String accountName);
}
