package com.wallet.mono.service.serviceImpl;

import com.wallet.mono.domain.dto.request.UserRequest;
import com.wallet.mono.domain.mapper.UserRequestMapper;
import com.wallet.mono.domain.model.User;
import com.wallet.mono.exception.UserNameAlreadyExistsException;
import com.wallet.mono.exception.UserNotFoundException;
import com.wallet.mono.repository.UserRepository;
import com.wallet.mono.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@AllArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRequestMapper userRequestMapper;
    private final UserRepository userRepository;

    @Override
    public void save(UserRequest userRequest) throws Exception {
        User user = userRequestMapper.mapToUser(userRequest);

        if (userRepository.existsByUserName(user.getUserName())){
            throw new UserNameAlreadyExistsException();
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserDetails(String userName) throws UserNotFoundException {
        User user = userRepository.findByUserName(userName);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public boolean existsUser(Integer id) throws UserNotFoundException {
        if (!userRepository.existsByUserId(id)){
            throw new UserNotFoundException();
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }
}
