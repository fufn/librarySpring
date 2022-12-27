package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.BookUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper implements Mapper<UserDto, BookUser> {

    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDto toDto(BookUser user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public BookUser toEntity(UserDto userDto) {
        return BookUser.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }

    @Override
    public List<UserDto> listToDto(List<BookUser> entities) {
        return null;
    }

    @Override
    public List<BookUser> listToEntity(List<UserDto> dtos) {
        return null;
    }
}
