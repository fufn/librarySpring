package com.example.demo.service.impl;

import com.example.demo.controller.handler.UserException;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.impl.UserMapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final static String USER_ALREADY_EXIST = "There is already an account with the same email.";
    private final Logger logger = LogManager.getLogger(getClass());
    @Override
    public UserDto saveUser(BookUser user) {
        logger.debug("Trying to save a user. " + user);
        BookUser existingUser = this.findByEmail(user.getEmail());

        if(existingUser != null){
            logger.debug("Such user already exists. " + user);
            throw new UserException(USER_ALREADY_EXIST);
        }

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
    public UserDto updateUser(BookUser user) {
        logger.debug("Trying to update the user " + user);
        BookUser newUser = userRepository.findByEmail(user.getEmail());
        logger.debug("User was found");
        newUser.setFullName(user.getFullName());
        logger.debug("Saving the user");
        return userMapper.toDto(userRepository.save(newUser));
    }

}
