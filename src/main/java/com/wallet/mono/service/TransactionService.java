package com.wallet.mono.service;

import com.wallet.mono.domain.dto.TransactionRequest;
import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.exception.TypeNotSelectedException;

import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionRequest transactionRequest) throws Exception, TypeNotSelectedException;
    List<TransactionResponse> getTransactionsByAccountId(Integer accountId) throws Exception;
}