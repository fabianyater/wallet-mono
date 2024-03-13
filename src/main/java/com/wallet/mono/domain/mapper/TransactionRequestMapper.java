package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.request.TransactionRequest;
import com.wallet.mono.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionRequestMapper {

    @Mapping(target = "transactionAmount", source = "amount")
    @Mapping(target = "transactionDescription", source = "description")
    @Mapping(target = "transactionType", source = "type")
    @Mapping(target = "category.categoryId", source = "categoryId")
    @Mapping(target = "wallet.walletId", source = "walletId")
    Transaction mapToTransaction(TransactionRequest transactionRequest);
}
