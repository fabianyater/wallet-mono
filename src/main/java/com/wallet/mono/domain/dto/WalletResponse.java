package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.wallet.mono.domain.model.Wallet}
 */
@Getter
@Setter
public class WalletResponse implements Serializable {
    Integer walletId;
    String name;
    String type;
    String currency;
    Double balance;
    String color;
    Boolean isExcluded;
    Integer accountId;
}