package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.request.TransactionRequest;
import com.wallet.mono.domain.dto.response.*;
import com.wallet.mono.domain.mapper.*;
import com.wallet.mono.domain.model.Transaction;
import com.wallet.mono.domain.model.Wallet;
import com.wallet.mono.enums.TransactionType;
import com.wallet.mono.exception.*;
import com.wallet.mono.repository.TransactionRepository;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.service.CategoryService;
import com.wallet.mono.service.TransactionService;
import com.wallet.mono.service.WalletService;
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
    private final WalletService walletService;
    private final WalletResponseMapper walletResponseMapper;
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

        WalletResponse walletResponse = walletService.getWalletDetails(transactionRequest.getWalletId());
        Wallet wallet = walletResponseMapper.toEntity(walletResponse);
        Transaction transaction = transactionRequestMapper.mapToTransaction(transactionRequest);

        setAccountBalance(wallet, transaction);

        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    public void saveTaxTransaction(Integer walletId) throws Exception {
        WalletResponse walletResponse = walletService.getWalletDetails(walletId);
        Wallet wallet = walletResponseMapper.toEntity(walletResponse);
        Double totalTransactionsAmount = transactionRepository.getTransactionsAmountByWalletId(walletId);
        Double totalAccountBalance = wallet.getBalance();

        Transaction transaction = new Transaction();

        transaction.setTransactionAmount(calculateGmf(totalTransactionsAmount, totalAccountBalance, walletId));
        transaction.setTransactionDescription("IMPUESTO GOBIERNO 4 X 1000");
        transaction.setCategory(categoryResponseMapper.mapToCategory(categoryService.getTaxCategory()));
        transaction.setTransactionType(TransactionType.EXPENSE.getValue());
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionTime(LocalTime.now());
        transaction.setWallet(wallet);

        transactionRepository.save(transaction);
    }

    @Override
    public ListTransactionResponse getTransactionsByUserId(Integer userId, Integer accountId, int page, int size) throws Exception {
        accountService.getAccountId(accountId);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "transaction_date", "time");
        Page<Object[]> transactions = transactionRepository.getAllTransactions(userId, accountId, pageable);

        List<TransactionResponse> transactionResponses = transactionResponseMapper.mapToObjectArrayList(transactions);
        ListTransactionResponse listTransactionResponse = new ListTransactionResponse();
        Long totalTransactions = transactionRepository.countTransactionByUserId(userId);

        listTransactionResponse.setTotalTransactions(totalTransactions);
        listTransactionResponse.setTransactions(transactionResponses);

        return listTransactionResponse;
    }

    @Override
    public TransactionResponse getTransactionDetails(int txnId, int walletId) throws Exception {
        walletService.getWalletDetails(walletId);

        if (!transactionRepository.existsByTransactionId(txnId)) {
            throw new TransactionDoesNotExists();
        }
        Transaction transaction = transactionRepository.findByTransactionIdAndWallet_WalletId(txnId, walletId);

        return transactionResponseMapper.mapToTransactionResponse(transaction);
    }

    @Override
    public TotalAmountResponse getTotalIncomeByAccountId(int accountId, Integer year, Integer month) {
        TotalAmountResponse totalAmountResponse = new TotalAmountResponse();
        List<Double> totalAmount = transactionRepository.getTotalTransactionAmountByWalletId(accountId, year, month);

        if (totalAmount.isEmpty()) {
            return totalAmountResponse;
        }

        totalAmountResponse.setExpense(totalAmount.get(0));
        totalAmountResponse.setIncome(totalAmount.get(1));

        return totalAmountResponse;
    }

//    @Override
//    public Boolean deleteAllTransactions(int walletId) {
//        List<Transaction> transactions = transactionRepository.findByWallet_WalletId(walletId, Pageable.unpaged()).getContent();
//
//        if (!transactions.isEmpty()) {
//            List<Integer> transactionIds = transactions.stream().map(Transaction::getTransactionId).toList();
//            transactionRepository.deleteAllById(transactionIds);
//            return true;
//        }
//
//        return false;
//    }

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

    private void setAccountBalance(Wallet wallet, Transaction transaction) throws Exception {
        Double totalBalance = wallet.getBalance();
        Double transactionAmount = transaction.getTransactionAmount();
        String type = transaction.getTransactionType();

        if (type.equalsIgnoreCase(TransactionType.INCOME.getValue())) {
            totalBalance += transactionAmount;
        }

        if (type.equalsIgnoreCase(TransactionType.EXPENSE.getValue())
                && hasAvailableBalance(totalBalance, transactionAmount)) {
            totalBalance -= transactionAmount;
        }

        walletService.updateWalletBalance(totalBalance, wallet.getWalletId());
    }

    private Double calculateGmf(Double totalTransactionsAmount, Double totalAccountBalance, Integer walletId) throws Exception {
        if (totalTransactionsAmount == null || totalTransactionsAmount == 0) {
            throw new CustomArithmeticException();
        }

        double gmf = (totalTransactionsAmount * 4) / 100;
        totalAccountBalance -= gmf;

        walletService.updateWalletBalance(totalAccountBalance, walletId);

        return gmf;
    }

    private boolean hasAvailableBalance(Double balance, Double amount) throws InsufficientBalanceException {
        if (balance >= amount) {
            return true;
        }

        throw new InsufficientBalanceException();
    }
}
