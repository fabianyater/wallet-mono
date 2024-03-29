package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer userId;
    private String userName;
    private String fullName;
    private String jwt;
    private String refreshToken;
    private long expirationDate;
    private String accountId;
}
