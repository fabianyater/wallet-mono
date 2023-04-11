package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {
    private String accountName;
    private String accountCurrency;
    private Double accountBalance;
    private String userId;
}
