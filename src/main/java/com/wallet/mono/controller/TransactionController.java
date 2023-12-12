package com.wallet.mono.controller;

import com.wallet.mono.domain.dto.*;
import com.wallet.mono.service.TransactionService;
import com.wallet.mono.utils.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;
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

    @GetMapping("wallet/{walletId}")
    public ResponseEntity<ApiResponse<ListTransactionResponse>> getTransactions(
            @PathVariable("walletId") Integer walletId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) throws Exception {
        try {
            ListTransactionResponse transactionResponses = transactionService.getTransactionsByWalletId(walletId, page - 1, size);
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

    @GetMapping("{txnId}/wallet/{walletId}")
    public ResponseEntity<TransactionResponse> getTransactionDetails(
            @PathVariable("txnId") Integer txnId,
            @PathVariable("walletId") Integer walletId) throws Exception {
        return new ResponseEntity<>(transactionService.getTransactionDetails(txnId, walletId), HttpStatus.OK);
    }

    @GetMapping("total/wallet/{walletId}")
    public ResponseEntity<TotalAmountResponse> getTotalTransactionsAmount(
            @PathVariable("walletId") Integer walletId,
            @PathParam("year") Integer year,
            @PathParam("month") Integer month) throws Exception {
        return new ResponseEntity<>(transactionService.getTotalIncomeByWalletId(walletId, year, month), HttpStatus.OK);
    }

    @GetMapping("stats/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionsSummaryResponse>>> getStats(
            @PathVariable("accountId") Integer accountId,
            @PathParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws Exception {
        List<TransactionsSummaryResponse> transactionStadistics = transactionService.getTransactionStatistics(accountId, startDate, endDate);
        ApiResponse<List<TransactionsSummaryResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setData(transactionStadistics);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage("Todo ok");
        apiResponse.setAdditionalInfo(null);
        apiResponse.setPagination(null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("monthly/summary/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionsSummaryResponse>>> getStats(
            @PathVariable("accountId") Integer accountId,
            @PathParam("year") int year,
            @PathParam("month") int month) throws Exception {
        List<TransactionsSummaryResponse> monthlyTransactionsSummary = transactionService.getDailyTransactionsSummary(accountId, year, month);
        ApiResponse<List<TransactionsSummaryResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setData(monthlyTransactionsSummary);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage("Todo ok");
        apiResponse.setAdditionalInfo(null);
        apiResponse.setPagination(null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("annual/summary/account/{accountId}")
    public ResponseEntity<ApiResponse<List<TransactionsSummaryResponse>>> getStats(
            @PathVariable("accountId") Integer accountId,
            @PathParam("year") int year) throws Exception {
        List<TransactionsSummaryResponse> monthlyTransactionsSummary = transactionService.getMonthlyTransactionsSummaryPerYear(accountId, year);
        ApiResponse<List<TransactionsSummaryResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setData(monthlyTransactionsSummary);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setMessage("Todo ok");
        apiResponse.setAdditionalInfo(null);
        apiResponse.setPagination(null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Boolean>> deleteAllTransactionsByAccountId(@RequestBody int accountId) {
        Boolean result = transactionService.deleteAllTransactions(accountId);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setData(result);
        apiResponse.setMessage(!result ? "No hay transacciones para eliminar" : "Transacciones eliminidas");
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setPagination(null);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
