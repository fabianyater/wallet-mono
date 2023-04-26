package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.TransactionRequest;
import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("transactions/")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> saveTransaction(@Valid @RequestBody TransactionRequest transactionRequest) throws Exception {
        transactionService.saveTransaction(transactionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable("accountId") Integer accountId) throws Exception {
        return new ResponseEntity<>(transactionService.getTransactionsByAccountId(accountId), HttpStatus.OK);
    }

}
