package com.project.userservice.service;

import com.project.userservice.dto.UserDto;
import com.project.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailByEmail(String email);
}
