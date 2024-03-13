package com.wallet.mono.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginatedWalletResponse {
    private int currentPage;
    private int itemsPerPage;
    private int totalItems;
    private List<WalletResponse> wallets;
}
