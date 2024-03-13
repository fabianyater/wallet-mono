package com.wallet.mono.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListTransactionResponse {
    private List<TransactionResponse> transactions;
    private Long totalTransactions;
}
