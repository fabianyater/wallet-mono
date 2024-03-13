package com.wallet.mono.service;

import com.wallet.mono.domain.dto.request.TransactionRequest;
import com.wallet.mono.domain.dto.response.ListTransactionResponse;
import com.wallet.mono.domain.dto.response.TotalAmountResponse;
import com.wallet.mono.domain.dto.response.TransactionResponse;
import com.wallet.mono.domain.dto.response.TransactionsSummaryResponse;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionRequest transactionRequest) throws Exception;
    void saveTaxTransaction(Integer walletId) throws Exception;
    ListTransactionResponse getTransactionsByUserId(Integer userId, Integer accountId, int page, int size) throws Exception;
    TransactionResponse getTransactionDetails(int txnId, int walletId) throws Exception;
    TotalAmountResponse getTotalIncomeByAccountId(int accountId, Integer year, Integer month);
    List<TransactionsSummaryResponse> getTransactionStatistics(int accountId, Date startDate, Date endDate) throws Exception;
    List<TransactionsSummaryResponse> getDailyTransactionsSummary(int accountId, int year, int month);
    List<TransactionsSummaryResponse> getMonthlyTransactionsSummaryPerYear(int accountId, int year);
}
