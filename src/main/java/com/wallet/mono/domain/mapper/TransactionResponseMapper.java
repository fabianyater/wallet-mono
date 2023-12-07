package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.domain.dto.TransactionStatistics;
import com.wallet.mono.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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


    default TransactionStatistics map(Object[] object) {
        TransactionStatistics statistics = new TransactionStatistics();
        statistics.setDay((String) object[0]);
        statistics.setIncome(((Number) object[1]).longValue());
        statistics.setExpense(((Number) object[2]).longValue());
        return statistics;
    }

    default List<TransactionStatistics> mapToTransactionStatistics(List<Object[]> objects) {
        if (objects == null) {
            return Collections.emptyList();
        }
        return objects.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
