package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.ListTransactionResponse;
import com.wallet.mono.domain.dto.TotalAmountResponse;
import com.wallet.mono.domain.dto.TransactionRequest;
import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.service.TransactionService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @PostMapping("gmf")
    public ResponseEntity<Void> saveTaxTransaction(@Valid @RequestBody Map<String, Object> payload) throws Exception {
        Integer accountId = (Integer) payload.get("accountId");
        transactionService.saveTaxTransaction(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("account/{accountId}")
    public ResponseEntity<ApiResponse<ListTransactionResponse>> getTransactions(
            @PathVariable("accountId") Integer accountId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws Exception {
        try {
            ListTransactionResponse transactionResponses = transactionService.getTransactionsByAccountId(accountId, page - 1, size);
            ApiResponse<ListTransactionResponse> apiResponse = new ApiResponse<>();

            ApiResponse.Pagination pagination = new ApiResponse.Pagination();
            pagination.setCurrentPage(page);
            pagination.setTotalItems(transactionResponses.getTotalTransactions());
            pagination.setItemsPerPage(size);
            pagination.setTotalPages(pagination.getTotalItems() / pagination.getItemsPerPage());

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Transaction data retrieved successfully.");
            apiResponse.setPagination(pagination);
            apiResponse.setData(transactionResponses);

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{txnId}/account/{accountId}")
    public ResponseEntity<TransactionResponse> getTransactionDetails(
            @PathVariable("txnId") Integer txnId,
            @PathVariable("accountId") Integer accountId) throws Exception {
        return new ResponseEntity<>(transactionService.getTransactionDetails(txnId, accountId), HttpStatus.OK);
    }

    @GetMapping("total/account/{accountId}")
    public ResponseEntity<TotalAmountResponse> getTotalTransactionsAmount(
            @PathVariable("accountId") Integer accountId) throws Exception {
        return new ResponseEntity<>(transactionService.getTotalIncomeByAccountId(accountId), HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactionsByAccountId(@RequestBody int accountId) {
        transactionService.deleteAllTransactions(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
