package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.WalletRequest;
import com.wallet.mono.domain.model.Wallet;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletRequestMapper {
    @Mapping(source = "accountId", target = "account.accountId")
    Wallet toEntity(WalletRequest walletRequest);

    @Mapping(source = "account.accountId", target = "accountId")
    WalletRequest toDto(Wallet wallet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "accountId", target = "account.accountId")
    Wallet partialUpdate(WalletRequest walletRequest, @MappingTarget Wallet wallet);
}