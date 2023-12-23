package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.WalletRequest;
import com.wallet.mono.domain.dto.WalletResponse;
import com.wallet.mono.service.WalletService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<WalletResponse>> getWalletsByAccountId(@PathVariable("accountId") Integer accountId) throws Exception {
        return new ResponseEntity<>(walletService.getAllWallets(accountId), HttpStatus.OK);
    }
}
