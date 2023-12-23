package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link com.wallet.mono.domain.model.Wallet}
 */
@Getter
@Setter
public class WalletRequest {
    String name;
    String type;
    String currency;
    String color;
    Double balance;
    Boolean isLimited;
    Double limitValue;
    Boolean isExcluded;
    Integer accountId;
}