package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequest {
    private int userId;
    private int accountId;
}
