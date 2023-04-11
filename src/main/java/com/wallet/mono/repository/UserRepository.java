package com.wallet.mono.repository;

import com.wallet.mono.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserId(Integer userId);
    boolean existsByUserName(String userName);

    User findByUserName(String userName);

}
