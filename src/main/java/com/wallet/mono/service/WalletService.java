package com.wallet.mono.service;

import com.wallet.mono.domain.dto.request.WalletRequest;
import com.wallet.mono.domain.dto.response.PaginatedWalletResponse;
import com.wallet.mono.domain.dto.response.WalletResponse;
import com.wallet.mono.exception.AccountNotFoundException;

public interface WalletService {

    void createWallet(WalletRequest walletRequest) throws AccountNotFoundException;

    PaginatedWalletResponse getAllWallets(int currentPage, int itemsPerPage, Integer accountId) throws AccountNotFoundException;
    WalletResponse getWalletDetails(Integer walletId);
    Double getWalletBalance(Integer walletId);
    void updateWalletBalance(Double newBalance, int walletId) throws Exception;
    void updateWalletByWalletId(int walletId, WalletRequest walletRequest) throws Exception;

}
