package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.*;
import com.wallet.mono.domain.mapper.*;
import com.wallet.mono.domain.model.Account;
import com.wallet.mono.domain.model.Transaction;
import com.wallet.mono.enums.TransactionType;
import com.wallet.mono.exception.CategoryNotSelectedException;
import com.wallet.mono.exception.CustomArithmeticException;
import com.wallet.mono.exception.InsufficientBalanceException;
import com.wallet.mono.exception.TransactionDoesNotExists;
import com.wallet.mono.exception.TypeNotSelectedException;
import com.wallet.mono.repository.TransactionRepository;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.CategoryService;
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
import java.util.Date;
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
    private final CategoryService categoryService;
    private final CategoryResponseMapper categoryResponseMapper;

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
    public void saveTaxTransaction(Integer accountId) throws Exception {
        AccountResponse accountResponse = accountService.getAccountId(accountId);
        Account account = accountResponseMapper.mapToAccount(accountResponse);
        Double totalTransactionsAmount = transactionRepository.getTransactionsAmountByAccountId(accountId);
        Double totalAccountBalance = account.getAccountBalance();

        Transaction transaction = new Transaction();

        transaction.setTransactionAmount(calculateGmf(totalTransactionsAmount, totalAccountBalance, accountId));
        transaction.setTransactionDescription("IMPUESTO GOBIERNO 4 X 1000");
        transaction.setCategory(categoryResponseMapper.mapToCategory(categoryService.getTaxCategory()));
        transaction.setTransactionType(TransactionType.EXPENSE.getValue());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());
        transaction.setAccount(account);

        transactionRepository.save(transaction);
    }

    @Override
    public ListTransactionResponse getTransactionsByAccountId(Integer accountId, int page, int size) throws Exception {
        accountService.getAccountId(accountId);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "transactionDate", "transactionTime");
        Page<Transaction> transactions = transactionRepository.findByAccount_AccountId(accountId, pageable);

        List<TransactionResponse> transactionResponses = transactionResponseMapper.mapToTransactionResponseList(transactions);
        ListTransactionResponse listTransactionResponse = new ListTransactionResponse();
        Long totalTransactions = transactionRepository.countByAccount_AccountId(accountId);

        listTransactionResponse.setTotalTransactions(totalTransactions);
        listTransactionResponse.setTransactions(transactionResponses);

        return listTransactionResponse;
    }

    @Override
    public TransactionResponse getTransactionDetails(int txnId, int accountId) throws Exception {
        accountService.getAccountId(accountId);

        if (!transactionRepository.existsByTransactionId(txnId)) {
            throw new TransactionDoesNotExists();
        }
        Transaction transaction = transactionRepository.findByTransactionIdAndAccount_AccountId(txnId, accountId);

        return transactionResponseMapper.mapToTransactionResponse(transaction);
    }

    @Override
    public TotalAmountResponse getTotalIncomeByAccountId(int accountId, Integer year, Integer month) {
        TotalAmountResponse totalAmountResponse = new TotalAmountResponse();
        List<Double> totalAmount = transactionRepository.getTotalTransactionAmountByAccountId(accountId, year, month);

        if (totalAmount.isEmpty()) {
            return totalAmountResponse;
        }

        totalAmountResponse.setExpense(totalAmount.get(0));
        totalAmountResponse.setIncome(totalAmount.get(1));

        return totalAmountResponse;
    }

    @Override
    public Boolean deleteAllTransactions(int accountId) {
        List<Transaction> transactions = transactionRepository.findByAccount_AccountId(accountId, Pageable.unpaged()).getContent();

        if (!transactions.isEmpty()) {
            List<Integer> transactionIds = transactions.stream().map(Transaction::getTransactionId).toList();
            transactionRepository.deleteAllById(transactionIds);
            return true;
        }

        return false;
    }

    @Override
    public List<TransactionsSummaryResponse> getTransactionStatistics(int accountId, Date startDate, Date endDate) {
        List<Object[]> transactionStatistics = transactionRepository.findWeeklyTransactionSummary(accountId, startDate, endDate);

        return transactionResponseMapper.mapToTransactionSummaryResponseList(transactionStatistics);
    }

    @Override
    public List<TransactionsSummaryResponse> getDailyTransactionsSummary(int accountId, int year, int month) {
        List<Object[]> monthlyTransactionsObject = transactionRepository.findDailyTransactionsSummary(accountId, year, month);

        return transactionResponseMapper.mapToTransactionSummaryResponseList(monthlyTransactionsObject);
    }

    @Override
    public List<TransactionsSummaryResponse> getMonthlyTransactionsSummaryPerYear(int accountId, int year) {
        List<Object[]> monthlyTransactionsObject = transactionRepository.findMonthlyTransactionsSummaryForYear(accountId, year);

        return transactionResponseMapper.mapToTransactionSummaryResponseList(monthlyTransactionsObject);
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

    private Double calculateGmf(Double totalTransactionsAmount, Double totalAccountBalance, Integer accountId) throws Exception {
        if (totalTransactionsAmount == null || totalTransactionsAmount == 0) {
            throw new CustomArithmeticException();
        }

        double gmf = (totalTransactionsAmount * 4) / 100;
        totalAccountBalance -= gmf;

        accountService.updateAccountBalance(totalAccountBalance, accountId);

        return gmf;
    }

    private boolean hasAvailableBalance(Double balance, Double amount) throws InsufficientBalanceException {
        if (balance >= amount) {
            return true;
        }

        throw new InsufficientBalanceException();
    }
}
