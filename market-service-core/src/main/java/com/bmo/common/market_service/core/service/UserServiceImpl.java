package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.mapper.UserMapper;
import com.bmo.common.market_service.core.repository.UserRepository;
import com.bmo.common.market_service.model.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User registerUser(RegisterUserDto registerUserDto) {
        User user = userMapper.map(registerUserDto);

        User saved = userRepository.save(user);
        return saved;
    }
}
