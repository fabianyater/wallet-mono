package com.wallet.mono.service;

import com.wallet.mono.domain.dto.response.AccountBalanceResponse;
import com.wallet.mono.domain.dto.request.AccountRequest;
import com.wallet.mono.domain.dto.response.AccountResponse;
import com.wallet.mono.exception.AccountNotFoundException;

import java.util.List;

public interface AccountService {
    void saveAccount(AccountRequest accountRequest) throws Exception;
    List<AccountResponse> getAccountsByUserId(int userId) throws Exception;
    AccountResponse getAccountId(int accountId) throws Exception;
    AccountResponse getAccountDetails(int accountId, int userId) throws Exception;
    AccountBalanceResponse getAccountBalance(int accountId) throws Exception;
    List<AccountBalanceResponse> getAccountBalanceByUserId(int userId) throws Exception;
    void updateAccount(int accountId, AccountRequest accountRequest) throws Exception;
    boolean doesAccountExist(int accountId) throws AccountNotFoundException;
}
