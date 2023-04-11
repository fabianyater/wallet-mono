package com.wallet.mono.domain.mapper;

import com.wallet.mono.domain.dto.UserRequest;
import com.wallet.mono.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {

    User mapToUser(UserRequest userRequest);
}
