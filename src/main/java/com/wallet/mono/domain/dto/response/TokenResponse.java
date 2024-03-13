package com.wallet.mono.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponse {
    private String jwt;
    private String refreshToken;
    private long expirationDate;
}
