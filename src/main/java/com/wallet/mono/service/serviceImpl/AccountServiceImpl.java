package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.mapper.AccountRequestMapper;
import com.wallet.mono.domain.mapper.AccountResponseMapper;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.exception.AccountAlreadyExistsException;
import com.wallet.mono.exception.UserNotFoundException;
import com.wallet.mono.repository.AccountRepository;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final AccountRequestMapper accountRequestMapper;
    private final AccountResponseMapper accountResponseMapper;

    @Override
    public void saveAccount(AccountRequest accountRequest) throws Exception {
        if (!userService.existsUser(Integer.parseInt(accountRequest.getUserId()))) {
            throw new UserNotFoundException();
        }

        List<AccountResponse> accounts = getAccountsByUserId(Integer.parseInt(accountRequest.getUserId()));

        if (accountNameAlreadyExists(accounts, accountRequest.getAccountName())){
            throw new AccountAlreadyExistsException();
        }

        Account account = accountRequestMapper.mapToAccount(accountRequest);
        accountRepository.save(account);

    }

    @Override
    public List<AccountResponse> getAccountsByUserId(int userId) throws Exception {
        if (!userService.existsUser(userId)) {
            throw new UserNotFoundException();
        }

        List<Account> accounts = accountRepository.findByUser_UserId(userId);

        return accountResponseMapper.mapToAccountResponseList(accounts);
    }

    private boolean accountNameAlreadyExists(List<AccountResponse> accountResponses, String name) {
        return accountResponses.stream().anyMatch(accountResponse -> accountResponse.getAccountName().equals(name));
    }
}
