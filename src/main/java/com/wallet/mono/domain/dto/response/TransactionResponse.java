package com.wallet.mono.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private Integer id;
    private Double amount;
    private String description;
    private String type;
    private String date;
    private String time;
    private String category;
    private String color;
    private String walletName;
}
