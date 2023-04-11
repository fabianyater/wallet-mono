package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByAccountName(String accountName);
}
