package com.spring.security.example.service;


import com.spring.security.example.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);
    UserDto updateUser(String userId, UserDto userDto);
    void deleteUser(String userId);
}
