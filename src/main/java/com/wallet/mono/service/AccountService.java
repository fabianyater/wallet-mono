package com.wallet.mono.service;

import com.wallet.mono.domain.dto.AccountRequest;

public interface AccountService {
    void saveAccount(AccountRequest accountRequest) throws Exception;

}
