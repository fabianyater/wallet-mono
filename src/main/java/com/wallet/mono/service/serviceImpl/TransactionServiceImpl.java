package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.dto.TransactionRequest;
import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.domain.mapper.AccountResponseMapper;
import com.wallet.mono.domain.mapper.TransactionRequestMapper;
import com.wallet.mono.domain.mapper.TransactionResponseMapper;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.domain.model.Transaction;
import com.wallet.mono.enums.TransactionType;
import com.wallet.mono.exception.CategoryNotSelectedException;
import com.wallet.mono.exception.InsufficientBalanceException;
import com.wallet.mono.exception.TypeNotSelectedException;
import com.wallet.mono.repository.TransactionRepository;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionRequestMapper transactionRequestMapper;
    private final TransactionResponseMapper transactionResponseMapper;
    private final AccountService accountService;
    private final AccountResponseMapper accountResponseMapper;

    @Override
    public void saveTransaction(TransactionRequest transactionRequest) throws Exception {
        if (transactionRequest.getCategoryId() == null) {
            throw new CategoryNotSelectedException();
        }

        if (transactionRequest.getType() == null) {
            throw new TypeNotSelectedException();
        }

        AccountResponse accountResponse = accountService.getAccountId(transactionRequest.getAccountId());
        Account account = accountResponseMapper.mapToAccount(accountResponse);
        Transaction transaction = transactionRequestMapper.mapToTransaction(transactionRequest);

        setAccountBalance(account, transaction);

        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionResponse> getTransactionsByAccountId(Integer accountId, Integer page, Integer size) throws Exception {
        accountService.getAccountId(accountId);
        Pageable pageable = PageRequest.of(page, size).withSort(Sort.Direction.ASC, "transactionDate");
        Page<Transaction> transactions = transactionRepository.findByAccount_AccountId(accountId, pageable);

        return transactionResponseMapper.mapToTransactionResponseList(transactions);
    }

    private void setAccountBalance(Account account, Transaction transaction) throws Exception {
        Double totalBalance = account.getAccountBalance();
        Double transactionAmount = transaction.getTransactionAmount();
        String type = transaction.getTransactionType();

        if (type.equalsIgnoreCase(TransactionType.INCOME.getValue())) {
            totalBalance += transactionAmount;
        }

        if (type.equalsIgnoreCase(TransactionType.EXPENSE.getValue())
                && hasAvailableBalance(totalBalance, transactionAmount)) {
            totalBalance -= transactionAmount;
        }

        accountService.updateAccountBalance(totalBalance, account.getAccountId());
    }

    private boolean hasAvailableBalance(Double balance, Double amount) throws InsufficientBalanceException {
        if (balance >= amount){
            return true;
        }

        throw new InsufficientBalanceException();
    }
}
