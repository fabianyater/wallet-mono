package com.wallet.mono.service;

import com.wallet.mono.domain.dto.*;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionRequest transactionRequest) throws Exception;
    void saveTaxTransaction(Integer accountId) throws Exception;
    ListTransactionResponse getTransactionsByAccountId(Integer accountId, int page, int size) throws Exception;
    TransactionResponse getTransactionDetails(int txnId, int accountId) throws Exception;
    TotalAmountResponse getTotalIncomeByAccountId(int accountId, Integer year, Integer month);
    Boolean deleteAllTransactions(int accountId);
    List<TransactionsSummaryResponse> getTransactionStatistics(int accountId, Date startDate, Date endDate) throws Exception;
    List<TransactionsSummaryResponse> getDailyTransactionsSummary(int accountId, int year, int month);
    List<TransactionsSummaryResponse> getMonthlyTransactionsSummaryPerYear(int accountId, int year);
}
