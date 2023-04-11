package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.mapper.AccountRequestMapper;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.exception.AccountAlreadyExistsException;
import com.wallet.mono.exception.UserNotFoundException;
import com.wallet.mono.repository.AccountRepository;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final AccountRequestMapper accountRequestMapper;

    @Override
    public void saveAccount(AccountRequest accountRequest) throws Exception {
        if (!userService.existsUser(Integer.parseInt(accountRequest.getUserId()))) {
            throw new UserNotFoundException();
        }

        if (accountRepository.existsByAccountName(accountRequest.getAccountName())){
            throw new AccountAlreadyExistsException();
        }

        Account account = accountRequestMapper.mapToAccount(accountRequest);
        accountRepository.save(account);

    }
}
