package com.wallet.mono.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
    private String categoryName;
    private String type;
    private String color;
    private int userId;
}
