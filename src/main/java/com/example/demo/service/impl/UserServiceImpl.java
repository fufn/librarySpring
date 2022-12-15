package com.example.demo.service.impl;

import com.example.demo.controller.handler.UserAlreadyExistHandler;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.impl.UserMapper;
import com.example.demo.entity.Role;
import com.example.demo.entity.BookUser;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final String userAlreadyExist = "There is alreade an account with the same email.";
    @Override
    public UserDto saveUser(UserDto userDto) {
        BookUser existingUser = this.findByEmail(userDto.getEmail());

        if(existingUser != null){
            throw new UserAlreadyExistHandler(userAlreadyExist);
        }

        BookUser user = userMapper.toEntity(userDto);
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(List.of(role));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public BookUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<UserDto> findAllUsers(Pageable pageable) {
        Page<BookUser> users = userRepository.findAll(pageable);
        return new PageImpl<>(users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        BookUser user = userRepository.findByEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        return userMapper.toDto(userRepository.save(user));
    }

}
