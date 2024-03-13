package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.request.WalletRequest;
import com.wallet.mono.domain.dto.response.PaginatedWalletResponse;
import com.wallet.mono.domain.dto.response.WalletResponse;
import com.wallet.mono.domain.mapper.WalletRequestMapper;
import com.wallet.mono.domain.mapper.WalletResponseMapper;
import com.wallet.mono.domain.model.Wallet;
import com.wallet.mono.exception.AccountNotFoundException;
import com.wallet.mono.repository.WalletRepository;
import com.wallet.mono.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final AccountServiceImpl accountService;
    private final WalletRequestMapper walletRequestMapper;
    private final WalletResponseMapper walletResponseMapper;


    @Override
    public void createWallet(WalletRequest walletRequest) throws AccountNotFoundException {
        boolean account = accountService.doesAccountExist(walletRequest.getAccountId());

        if (account) {
            Wallet wallet = walletRequestMapper.toEntity(walletRequest);
            if (walletRequest.getType().equals("creditCard")) {
                wallet.setBalance(walletRequest.getCreditLimit());
            }
            walletRepository.save(wallet);
        }
    }

    @Override
    public PaginatedWalletResponse getAllWallets(int currentPage, int itemsPerPage, Integer accountId) throws AccountNotFoundException {
        boolean account = accountService.doesAccountExist(accountId);
        List<WalletResponse> walletResponse = new ArrayList<>();
        List<Wallet> wallets;
        int totalItems;
        int startIndex = (currentPage - 1) * itemsPerPage;

        if (account) {
            wallets = walletRepository.findByAccount_AccountId(accountId);
            walletResponse = wallets.stream()
                    .map(walletResponseMapper::toDto)
                    .toList();
        }

        totalItems = walletResponse.size();

        PaginatedWalletResponse paginatedWalletResponse = new PaginatedWalletResponse();
        paginatedWalletResponse.setCurrentPage(currentPage);
        paginatedWalletResponse.setTotalItems(totalItems);
        paginatedWalletResponse.setWallets(walletResponse.stream()
                .skip(startIndex)
                .limit(itemsPerPage)
                .toList());

        return paginatedWalletResponse;
    }

    @Override
    public WalletResponse getWalletDetails(Integer walletId) {
        boolean walletExists = walletRepository.existsByWalletId(walletId);

        if (walletExists) {
            Wallet wallet = walletRepository.findByWalletId(walletId);
            return walletResponseMapper.toDto(wallet);
        }

        return null;
    }

    @Override
    public Double getWalletBalance(Integer walletId) {
        boolean walletExists = walletRepository.existsByWalletId(walletId);

        if (walletExists) {
            return walletRepository.findBalanceByWalletId(walletId);
        }

        return null;
    }

    @Override
    public void updateWalletBalance(Double newBalance, int walletId) throws Exception {
        if (walletRepository.existsById(walletId)) {
            walletRepository.updateBalanceByWalletId(newBalance, walletId);
        }

    }

    @Override
    public void updateWalletByWalletId(int walletId, WalletRequest walletRequest) throws Exception {
        WalletResponse walletResponse = getWalletDetails(walletId);
        if (walletResponse != null) {
            walletResponse.setName(walletRequest.getName());
            walletResponse.setType(walletRequest.getType());
            walletResponse.setColor(walletRequest.getColor());
            walletResponse.setCurrency(walletRequest.getCurrency());
            walletResponse.setBalance(walletRequest.getBalance());
            walletResponse.setIsExcluded(walletRequest.getIsExcluded());
            walletResponse.setAccountId(walletRequest.getAccountId());

            if (walletRequest.getType().equals("creditCard")) {
                walletResponse.setCreditLimit(walletRequest.getCreditLimit());
                walletResponse.setDuePaymentDay(walletRequest.getDuePaymentDay());
                walletResponse.setStatementDay(walletRequest.getStatementDay());
            }

            Wallet wallet = walletResponseMapper.toEntity(walletResponse);

            walletRepository.save(wallet);
        } else {
            throw new Exception("Wallet not found");
        }
    }
}