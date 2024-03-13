package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.response.AccountBalanceResponse;
import com.wallet.mono.domain.dto.request.AccountRequest;
import com.wallet.mono.domain.dto.response.AccountResponse;
import com.wallet.mono.domain.mapper.AccountRequestMapper;
import com.wallet.mono.domain.mapper.AccountResponseMapper;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.exception.AccountAlreadyExistsException;
import com.wallet.mono.exception.AccountNotFoundException;
import com.wallet.mono.exception.UserNotFoundException;
import com.wallet.mono.repository.AccountRepository;
import com.wallet.mono.repository.TransactionRepository;
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
    private final TransactionRepository transactionRepository;

    @Override
    public void saveAccount(AccountRequest accountRequest) throws Exception {
        if (!userService.existsUser(Integer.parseInt(accountRequest.getUserId()))) {
            throw new UserNotFoundException();
        }

        List<AccountResponse> accounts = getAccountsByUserId(Integer.parseInt(accountRequest.getUserId()));

        if (accountNameAlreadyExists(accounts, accountRequest.getAccountName())) {
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

        List<AccountResponse> accountResponses =  accountResponseMapper.mapToAccountResponseList(accounts);


        for (AccountResponse accountResponse : accountResponses) {
            AccountBalanceResponse accountBalanceResponse = getAccountBalance(Integer.parseInt(accountResponse.getAccountId()));
            accountResponse.setAccountBalance(accountBalanceResponse.getAccountBalance());
        }

        return accountResponses;
    }

    @Override
    public AccountResponse getAccountId(int accountId) throws Exception {
        Account account = accountRepository.findByAccountId(accountId);

        if (account == null) {
            throw new AccountNotFoundException();
        }

        return accountResponseMapper.mapToAccountResponse(account);
    }

    @Override
    public AccountResponse getAccountDetails(int accountId, int userId) throws Exception {
        if (!accountRepository.existsByAccountId(accountId)) {
            throw new AccountNotFoundException();
        }

        if (!userService.existsUser(userId)) {
            throw new UserNotFoundException();
        }

        Account account = accountRepository.findByAccountIdAndUser_UserId(accountId, userId);

        return accountResponseMapper.mapToAccountResponse(account);
    }

    @Override
    public AccountBalanceResponse getAccountBalance(int accountId) throws Exception {
        if (!accountRepository.existsByAccountId(accountId)) {
            throw new AccountNotFoundException();
        }

        Double balance = accountRepository.findGeneralAccountBalance(accountId);
        AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
        accountBalanceResponse.setAccountBalance(balance);

        return accountBalanceResponse;
    }

    @Override
    public List<AccountBalanceResponse> getAccountBalanceByUserId(int userId) throws Exception {
        List<Object[]> accountBalances = accountRepository.findGeneralAccountBalanceByUserId(userId);
        return accountBalances.stream()
                .map(accountBalance -> {
                    AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
                    accountBalanceResponse.setAccountBalance((Double) accountBalance[0]);
                    return accountBalanceResponse;
                })
                .toList();
    }

    @Override
    public void updateAccount(int accountId, AccountRequest accountRequest) throws Exception {
        AccountResponse accountResponse = getAccountDetails(accountId, Integer.parseInt(accountRequest.getUserId()));

        accountResponse.setAccountName(accountRequest.getAccountName());
        accountResponse.setAccountCurrency(accountRequest.getAccountCurrency());
        accountResponse.setAccountBalance(accountRequest.getAccountBalance());
        accountResponse.setUserId(Integer.parseInt(accountRequest.getUserId()));

        Account account = accountResponseMapper.mapToAccount(accountResponse);

        accountRepository.save(account);

    }

    @Override
    public boolean doesAccountExist(int accountId) throws AccountNotFoundException {
        if (accountRepository.existsByAccountId(accountId)) {
            return true;
        } else {
            throw new AccountNotFoundException();
        }
    }

    private boolean accountNameAlreadyExists(List<AccountResponse> accountResponses, String name) {
        return accountResponses.stream().anyMatch(accountResponse -> accountResponse.getAccountName().equals(name));
    }
}
