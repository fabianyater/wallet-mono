package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.AccountBalanceResponse;
import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.dto.FavoriteRequest;
import com.wallet.mono.domain.mapper.AccountRequestMapper;
import com.wallet.mono.domain.mapper.AccountResponseMapper;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.exception.AccountAlreadyExistsException;
import com.wallet.mono.exception.AccountNotFoundException;
import com.wallet.mono.exception.UnableToDeleteFavoriteAccount;
import com.wallet.mono.exception.UserNotFoundException;
import com.wallet.mono.repository.AccountRepository;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        if (accountNameAlreadyExists(accounts, accountRequest.getAccountName())) {
            throw new AccountAlreadyExistsException();
        }

        Account account = accountRequestMapper.mapToAccount(accountRequest);

        if  (accounts.isEmpty()) {
            account.setFavorite(true);
        }

        accountRepository.save(account);

    }

    @Override
    public List<AccountResponse> getAccountsByUserId(int userId) throws Exception {
        if (!userService.existsUser(userId)) {
            throw new UserNotFoundException();
        }

        List<Account> accounts = accountRepository.findByUser_UserId(userId);

        accounts.sort(Comparator.comparing(Account::isFavorite).reversed());

        return accountResponseMapper.mapToAccountResponseList(accounts);
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

        Double balance = accountRepository.findAccountBalance(accountId);
        AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
        accountBalanceResponse.setAccountBalance(balance);

        return accountBalanceResponse;
    }

    @Override
    public void updateAccountBalance(Double newBalance, int accountId) throws Exception {
        getAccountId(accountId);
        accountRepository.updateAccountBalanceByAccountId(newBalance, accountId);
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
    public void updateDefaultAccount(FavoriteRequest favoriteRequest) throws Exception {
        int userId = favoriteRequest.getUserId();
        int accountId = favoriteRequest.getAccountId();

        Optional<AccountResponse> oldDefaultAccount = getAccountsByUserId(userId)
                .stream()
                .filter(AccountResponse::isFavorite)
                .findFirst();

        oldDefaultAccount.ifPresent(accountResponse -> accountRepository
                .updateIsFavoriteByAccountId(false, Integer.parseInt(accountResponse.getAccountId())));

        accountRepository.updateIsFavoriteByAccountId(true, accountId);
    }

    @Override
    public void deleteAccount(FavoriteRequest favoriteRequest) throws Exception {
        AccountResponse accountResponse = getAccountDetails(favoriteRequest.getAccountId(), favoriteRequest.getUserId());

        if (accountResponse.isFavorite()) {
            throw new UnableToDeleteFavoriteAccount();
        }

        accountRepository.deleteById(favoriteRequest.getAccountId());
    }

    private boolean accountNameAlreadyExists(List<AccountResponse> accountResponses, String name) {
        return accountResponses.stream().anyMatch(accountResponse -> accountResponse.getAccountName().equals(name));
    }
}
