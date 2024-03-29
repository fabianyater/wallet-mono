package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByAccountId(Integer accountId);
    Account findByAccountIdAndUser_UserId(Integer accountId, Integer userId);
    boolean existsByAccountId(Integer accountId);
    List<Account> findByUser_UserId(Integer userId);
    @Query(value = "select sum(w.balance) from wallets w where w.account_id = :accountId", nativeQuery = true)
    Double findGeneralAccountBalance(@Param("accountId") Integer accountId);

    @Query(value = "select sum(w.balance), a.account_name from wallets w " +
            "inner join accounts a on w.account_id = a.id " +
            "inner join users u on a.user_id = u.id " +
            "where u.id = :userId " +
            "group by a.account_name", nativeQuery = true)
    List<Object[]> findGeneralAccountBalanceByUserId(@Param("userId") Integer userId);
}
