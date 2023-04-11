package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.AccountRequest;
import com.wallet.mono.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountRequestMapper {

    @Mapping(source = "userId", target = "user.userId")
    Account mapToAccount(AccountRequest accountRequest);
}
