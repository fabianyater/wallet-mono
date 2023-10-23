package com.wallet.mono.service;

import com.wallet.mono.domain.dto.ListTransactionResponse;
import com.wallet.mono.domain.dto.TotalAmountResponse;
import com.wallet.mono.domain.dto.TransactionRequest;
import com.wallet.mono.domain.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionRequest transactionRequest) throws Exception;
    void saveTaxTransaction(Integer accountId) throws Exception;
    ListTransactionResponse getTransactionsByAccountId(Integer accountId, int page, int size) throws Exception;
    TransactionResponse getTransactionDetails(int txnId, int accountId) throws Exception;
    TotalAmountResponse getTotalIncomeByAccountId(int accountId);
}
