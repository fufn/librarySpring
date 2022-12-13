package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper implements Mapper<UserDto, User> {

    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto toDto(User user) {
        String[] str = user.getFull_name().split(" ");
        return UserDto.builder()
                .id(user.getId())
                .firstName(str[0])
                .lastName(str[1])
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        return User.builder()
                .full_name(userDto.getFirstName() + " " + userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }

    @Override
    public List<UserDto> listToDto(List<User> entities) {
        return null;
    }

    @Override
    public List<User> listToEntity(List<UserDto> dtos) {
        return null;
    }
}
