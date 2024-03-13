package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.response.AccountResponse;
import com.wallet.mono.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountResponseMapper {

    @Mapping(target = "userId", source = "user.userId")
    AccountResponse mapToAccountResponse(Account account);

    @Mapping(target = "user.userId", source = "userId")
    Account mapToAccount(AccountResponse accountResponse);

    List<AccountResponse> mapToAccountResponseList(List<Account> accounts);
}
