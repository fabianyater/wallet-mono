package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatistics {
    private String day;
    private long income;
    private long expense;
}
