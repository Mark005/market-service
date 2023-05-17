package com.bmo.common.market_service.core.service;

import com.bmo.common.market_service.core.dbmodel.Cart;
import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.dbmodel.enums.UserStatus;
import com.bmo.common.market_service.core.mapper.EnumMapper;
import com.bmo.common.market_service.core.mapper.PageableMapper;
import com.bmo.common.market_service.core.mapper.user.UserMapper;
import com.bmo.common.market_service.core.repository.CartRepository;
import com.bmo.common.market_service.core.repository.UserRepository;
import com.bmo.common.market_service.core.repository.specification.UserSpecification;
import com.bmo.common.market_service.model.exception.EntityNotFoundException;
import com.bmo.common.market_service.model.user.RegisterUserDto;
import com.bmo.common.market_service.model.user.UpdateUserDto;
import com.bmo.common.market_service.model.user.UsersFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private final UserMapper userMapper;
    private final EnumMapper enumMapper;

    @Override
    public Page<User> getUsersFiltered(UsersFilterCriteria filter) {
        Specification<User> specification =
                Specification.where(UserSpecification.name(filter.getName()))
                .and(UserSpecification.surname(filter.getSurname()))
                .and(UserSpecification.email(filter.getEmail()))
                .and(UserSpecification.status(enumMapper.map(filter.getStatus())))
                .and(UserSpecification.gender(enumMapper.map(filter.getGender())));

        return userRepository.findAll(specification, PageableMapper.map(filter));
    }

    @Override
    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id {%s} not found".formatted(userId)));
    }

    @Override
    @Transactional
    public User registerUser(@NotNull UUID securityUserId, RegisterUserDto registerUserDto) {
        User user = userMapper.map(securityUserId, registerUserDto);

        Cart cart = new Cart();
        cartRepository.save(cart);

        user.setStatus(UserStatus.ACTIVE);
        user.setCart(cart);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User updateUserInfoById(UUID userId, UpdateUserDto updateUserDto) {
        User userById = getUserById(userId);
        User user = userMapper.merge(userById, updateUserDto);

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.updateStatusById(userId, UserStatus.DELETED);
    }

    @Override
    public void recoverUserById(UUID userId) {
        userRepository.updateStatusById(userId, UserStatus.ACTIVE);
    }
}
