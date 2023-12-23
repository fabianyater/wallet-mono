package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.WalletRequest;
import com.wallet.mono.domain.dto.WalletResponse;
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

        if(account) {
            Wallet wallet = walletRequestMapper.toEntity(walletRequest);
            walletRepository.save(wallet);
        }
    }

    @Override
    public List<WalletResponse> getAllWallets(Integer accountId) throws AccountNotFoundException {
        boolean account = accountService.doesAccountExist(accountId);
        List<WalletResponse> walletResponse = new ArrayList<>();
        List<Wallet> wallets;

        if (account) {
            wallets = walletRepository.findByAccount_AccountId(accountId);
            return wallets.stream()
                    .map(walletResponseMapper::toDto)
                    .toList();
        }

        return walletResponse;
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
}