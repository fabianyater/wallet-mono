package com.wallet.mono.service;

import com.wallet.mono.domain.dto.AccountBalanceResponse;
import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.dto.AccountResponse;

import java.util.List;

public interface AccountService {
    void saveAccount(AccountRequest accountRequest) throws Exception;
    List<AccountResponse> getAccountsByUserId(int userId) throws Exception;
    AccountResponse getAccountDetails(int accountId, int userId) throws Exception;
    AccountBalanceResponse getAccountBalance(int accountId) throws Exception;
}
