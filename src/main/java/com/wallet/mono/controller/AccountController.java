package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.AccountBalanceResponse;
import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.service.AccountService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("accounts/")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> saveUser(@RequestBody AccountRequest accountRequest) throws Exception {
        accountService.saveAccount(accountRequest);
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Cuenta creada correctamente");
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData(null);
        apiResponse.setPagination(null);
        apiResponse.setAdditionalInfo(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable("userId") Integer userId) throws Exception {
        return new ResponseEntity<>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("{accountId}/user/{userId}")
    public ResponseEntity<AccountResponse> getAccountDetails(
            @PathVariable("accountId") Integer accountId,
            @PathVariable("userId") Integer userId) throws Exception {
        return new ResponseEntity<>(accountService.getAccountDetails(accountId, userId), HttpStatus.OK);
    }

    @GetMapping("{accountId}/balance")
    public ResponseEntity<AccountBalanceResponse> getAccountBalance(
            @PathVariable("accountId") Integer accountId) throws Exception {
        return new ResponseEntity<>(accountService.getAccountBalance(accountId), HttpStatus.OK);
    }

    @PutMapping("{accountId}")
    public ResponseEntity<Void> updateAccount(
            @PathVariable("accountId") Integer accountId,
            @RequestBody AccountRequest accountRequest) throws Exception {
        accountService.updateAccount(accountId, accountRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
