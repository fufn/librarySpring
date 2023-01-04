package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.BookUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
                .books(user.getBooks())
                .build();
    }

    @Override
    public BookUser toEntity(UserDto userDto) {
        return BookUser.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .books(userDto.getBooks())
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
