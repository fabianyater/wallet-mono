package com.wallet.mono.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link com.wallet.mono.domain.model.Wallet}
 */
@Getter
@Setter
public class WalletRequest {
    private String name;
    private String type;
    private String currency;
    private String color;
    private Double balance;
    private Boolean isExcluded;
    private Double creditLimit;
    private int statementDay;
    private int duePaymentDay;
    private Integer accountId;
}