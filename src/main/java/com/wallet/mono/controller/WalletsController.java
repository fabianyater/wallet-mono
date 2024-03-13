package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.request.WalletRequest;
import com.wallet.mono.domain.dto.response.PaginatedWalletResponse;
import com.wallet.mono.domain.dto.response.WalletResponse;
import com.wallet.mono.service.WalletService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("wallets/")
public class WalletsController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveUser(@RequestBody WalletRequest walletRequest) throws Exception {
        walletService.createWallet(walletRequest);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Billetera creada correctamente");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(null);
        apiResponse.setPagination(null);
        apiResponse.setAdditionalInfo(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("account/{accountId}")
    public ResponseEntity<PaginatedWalletResponse> getWalletsByAccountId(
            @RequestParam(value = "page") int currentPage,
            @RequestParam("items") int itemsPerPage,
            @PathVariable("accountId") Integer accountId) throws Exception {
        return new ResponseEntity<>(walletService.getAllWallets(currentPage, itemsPerPage, accountId), HttpStatus.OK);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getWalletDetails(@PathVariable("walletId") Integer walletId) {
        return new ResponseEntity<>(walletService.getWalletDetails(walletId), HttpStatus.OK);
    }


    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse<Void>> updateWallet(@PathVariable("id") Integer walletId, @RequestBody WalletRequest walletRequest) throws Exception {
        walletService.updateWalletByWalletId(walletId, walletRequest);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Billetera actualizada correctamente");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(null);
        apiResponse.setPagination(null);
        apiResponse.setAdditionalInfo(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
