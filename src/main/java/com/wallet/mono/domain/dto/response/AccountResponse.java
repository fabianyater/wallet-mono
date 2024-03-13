package com.wallet.mono.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private String accountId;
    private String accountName;
    private String accountCurrency;
    private Double accountBalance;
    private String color;
    private int userId;
}
