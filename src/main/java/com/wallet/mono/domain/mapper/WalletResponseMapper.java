package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.WalletResponse;
import com.wallet.mono.domain.model.Wallet;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletResponseMapper {
    Wallet toEntity(WalletResponse walletResponse);

    WalletResponse toDto(Wallet wallet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Wallet partialUpdate(WalletResponse walletResponse, @MappingTarget Wallet wallet);
}