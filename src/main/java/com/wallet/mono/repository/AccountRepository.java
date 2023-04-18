package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByAccountIdAndUser_UserId(Integer accountId, Integer userId);
    boolean existsByAccountId(Integer accountId);
    List<Account> findByUser_UserId(Integer userId);
    @Query(value = "select a.account_balance from accounts a where a.id = :id", nativeQuery = true)
    Double findAccountBalance(@Param("id") Integer id);
}
