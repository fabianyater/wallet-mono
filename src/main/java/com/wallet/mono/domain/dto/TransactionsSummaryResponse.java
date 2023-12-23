package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionsSummaryResponse {
    private String dateName;
    private double income;
    private double expense;
}
