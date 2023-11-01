package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDeleteRequest {
    private int categoryId;
    private int userId;
}
