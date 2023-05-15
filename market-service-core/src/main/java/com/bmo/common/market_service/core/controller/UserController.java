package com.bmo.common.market_service.core.controller;

import com.bmo.common.market_service.core.dbmodel.User;
import com.bmo.common.market_service.core.mapper.UserMapper;
import com.bmo.common.market_service.core.service.UserService;
import com.bmo.common.market_service.model.RegisterUserDto;
import com.bmo.common.market_service.model.RegisterUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;


  @PostMapping("/users/register")
  public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
    User user = userService.registerUser(registerUserDto);
    RegisterUserResponseDto responseDto = userMapper.map(user);
    return ResponseEntity.ok(responseDto);
  }
}
