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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class.toString());
    @Override
    public UserDto saveUser(UserDto userDto) {
        logger.debug("Trying to save a user. " + userDto);
        BookUser existingUser = this.findByEmail(userDto.getEmail());

        if(existingUser != null){
            logger.debug("Such user already exists. " + userDto);
            throw new UserAlreadyExistHandler(userAlreadyExist);
        }

        BookUser user = userMapper.toEntity(userDto);
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(List.of(role));
        logger.debug("Saving user.");
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public BookUser findByEmail(String email) {
        logger.debug("Finding the user by email = " + email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<UserDto> findAllUsers(Pageable pageable) {
        logger.debug("Trying to find all users for page " + pageable);
        Page<BookUser> users = userRepository.findAll(pageable);
        logger.debug("Found users: " + users);
        return new PageImpl<>(users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteById(Long id) {
        logger.debug("Trying to delete the user with id = " + id );
        userRepository.deleteById(id);
        logger.debug("Successfully deleted");
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        logger.debug("Trying to update the user " + userDto);
        BookUser user = userRepository.findByEmail(userDto.getEmail());
        logger.debug("User was found");
        user.setFullName(userDto.getFullName());
        logger.debug("Saving the user");
        return userMapper.toDto(userRepository.save(user));
    }

}
