package com.wallet.mono.repository;

import com.wallet.mono.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    List<Wallet> findByAccount_AccountId(Integer accountId);

    @Query(value = "select w.balance from wallets w where w.id = ?1", nativeQuery = true)
    Double findBalanceByWalletId(Integer walletId);

    boolean existsByWalletId(Integer walletId);

    Wallet findByWalletId(Integer walletId);
    @Transactional
    @Modifying
    @Query("update Wallet w set w.balance = ?1 where w.walletId = ?2")
    int updateBalanceByWalletId(Double balance, Integer walletId);
}