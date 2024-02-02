package com.wallet.mono.service;

import com.wallet.mono.domain.dto.WalletRequest;
import com.wallet.mono.domain.dto.WalletResponse;
import com.wallet.mono.exception.AccountNotFoundException;

import java.util.List;

public interface WalletService {

    void createWallet(WalletRequest walletRequest) throws AccountNotFoundException;

    List<WalletResponse> getAllWallets(Integer accountId) throws AccountNotFoundException;
    WalletResponse getWalletDetails(Integer walletId);
    Double getWalletBalance(Integer walletId);
    void updateWalletBalance(Double newBalance, int walletId) throws Exception;
    void updateWalletByWalletId(int walletId, WalletRequest walletRequest) throws Exception;

}
