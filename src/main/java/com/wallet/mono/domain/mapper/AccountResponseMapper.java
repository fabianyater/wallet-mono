package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.AccountResponse;
import com.wallet.mono.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountResponseMapper {
    AccountResponse mapToAccountResponse(Account account);
    Account mapToAccount(AccountResponse accountResponse);

    List<AccountResponse> mapToAccountResponseList(List<Account> accounts);
}
