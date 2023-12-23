package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.TransactionResponse;
import com.wallet.mono.domain.dto.TransactionsSummaryResponse;
import com.wallet.mono.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
    @Mapping(target = "category", source = "category.categoryName")
    TransactionResponse mapToTransactionResponse(Transaction transaction);

    List<TransactionResponse> mapToTransactionResponseList(List<Transaction> transactions);

    List<TransactionResponse> mapToTransactionResponseList(Page<Transaction> transactions);

    default TransactionResponse mapFromObjectArray(Object[] array) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.println(array[4].getClass().getName());
        TransactionResponse response = new TransactionResponse();
        if (array != null && array.length >= 9) { // Asegúrate de que haya suficientes elementos
            response.setId((Integer) array[0]);
            response.setCategory((String) array[1]); // Suponiendo que el primer elemento es el nombre de la categoría
            response.setColor((String) array[2]);
            response.setAmount(array[3] != null ? ((Number) array[3]).doubleValue() : null); // Suponiendo que el segundo elemento es el monto
            if (array[4] != null && array[4] instanceof Date) {
                String formattedDate = dateFormat.format((Date) array[4]);
                response.setDate(formattedDate);
            }
            response.setType(array[5] != null ? (String) array[5] : null); // Suponiendo que el cuarto elemento es el tipo
            if (array[6] != null && array[6] instanceof Time) {
                String formattedTime = timeFormat.format((Time) array[6]);
                response.setTime(formattedTime);
            }
            response.setDescription(array[7] != null ? (String) array[7] : null); // Suponiendo que el sexto elemento es la descripción
            response.setWalletName(array[8] != null ? (String) array[8] : null); // Suponiendo que el séptimo elemento es el nombre de la billetera
        }
        return response;
    }

    default TransactionsSummaryResponse mapToTransactionSummaryResponse(Object[] object) {
        TransactionsSummaryResponse transactionsSummaryResponse = new TransactionsSummaryResponse();
        transactionsSummaryResponse.setDateName((String) object[0]);
        transactionsSummaryResponse.setIncome(((Number) object[1]).longValue());
        transactionsSummaryResponse.setExpense(((Number) object[2]).longValue());
        return transactionsSummaryResponse;
    }

    default List<TransactionsSummaryResponse> mapToTransactionSummaryResponseList(List<Object[]> objects) {
        if (objects == null) {
            return Collections.emptyList();
        }
        return objects.stream()
                .map(this::mapToTransactionSummaryResponse)
                .collect(Collectors.toList());
    }

    default List<TransactionResponse> mapToObjectArrayList(Page<Object[]> objects) {
        if (objects == null) {
            return Collections.emptyList();
        }
        return objects.stream()
                .map(this::mapFromObjectArray)
                .toList();
    }
}
