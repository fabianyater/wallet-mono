package com.wallet.mono.domain.dto;

import com.wallet.mono.utils.AdditionalInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdditionalInfoResponse {
    private List<CategoryResponse> categoryResponse;
    private AdditionalInfo additionalInfo;
}
