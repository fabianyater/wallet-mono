package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.AccountBalanceResponse;
import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.dto.FavoriteRequest;
import com.wallet.mono.service.AccountService;
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

    @PostMapping()
    public ResponseEntity<Void> saveUser(@RequestBody AccountRequest accountRequest) throws Exception {
        accountService.saveAccount(accountRequest);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping("favorite")
    public ResponseEntity<Void> updateDefaultAccount(
            @RequestBody FavoriteRequest favoriteRequest) throws Exception {
        accountService.updateDefaultAccount(favoriteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
