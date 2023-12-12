package com.wallet.mono.domain.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.wallet.mono.domain.model.Wallet}
 */
@Value
public class WalletResponse implements Serializable {
    Integer walletId;
    String name;
    String type;
    String currency;
    Double balance;
    Boolean isLimited;
    Double limitValue;
    Boolean isExcluded;
}