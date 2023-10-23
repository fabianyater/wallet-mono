package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionResponseMapper {

    @Mapping(target = "id", source = "transactionId")
    @Mapping(target = "amount", source = "transactionAmount")
    @Mapping(target = "description", source = "transactionDescription")
    @Mapping(target = "type", source = "transactionType")
    @Mapping(target = "date", source = "transactionDate")
    @Mapping(target = "time", source = "transactionTime")
    @Mapping(target = "category.categoryName", source = "category.categoryName")
    TransactionResponse mapToTransactionResponse(Transaction transaction);

    List<TransactionResponse> mapToTransactionResponseList(List<Transaction> transactions);
    List<TransactionResponse> mapToTransactionResponseList(Page<Transaction> transactions);
}
