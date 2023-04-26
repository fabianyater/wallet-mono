package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private Double amount;
    private String description;
    private String type;
    private Integer categoryId;
    private Integer accountId;
}
