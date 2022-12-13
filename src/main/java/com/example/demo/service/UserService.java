package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto);
    User findByEmail(String email);

    List<UserDto> findAllUsers(Pageable pageable);
    void deleteById(Long id);
    UserDto updateUser(UserDto userDto);


}
