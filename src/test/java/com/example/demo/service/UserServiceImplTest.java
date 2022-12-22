package com.example.demo.service;

import com.example.demo.controller.handler.UserAlreadyExistHandler;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.impl.UserMapper;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;
    @Test
    public void testSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFullName("Test User");
        userDto.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        BookUser user = new BookUser();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        user.setRoles(List.of(role));

        when(userMapper.toEntity(userDto)).thenReturn(user);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto savedUser = userService.saveUser(userDto);
        assertNotNull(savedUser);

        verify(userRepository, times(1)).save(user);

        assertEquals(userDto, savedUser);
    }

    @Test
    public void testSaveUser_UserAlreadyExists() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFullName("Test User");
        userDto.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        BookUser user = new BookUser();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setPassword(userDto.getPassword());
        user.setRoles(List.of(role));

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        assertThrows(UserAlreadyExistHandler.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void testFindByEmail() {
        String email = "test@example.com";
        BookUser user = new BookUser();
        user.setEmail(email);
        user.setFullName("Test User");
        user.setPassword("password");

        when(userRepository.findByEmail(email)).thenReturn(user);

        BookUser foundUser = userService.findByEmail(email);

        verify(userRepository, times(1)).findByEmail(email);
        assertEquals(user, foundUser);
    }

    @Test
    public void testUpdateUser() {
        String email = "test@example.com";
        String fullName = "Test User";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setFullName(fullName);
        userDto.setPassword(password);

        BookUser user = new BookUser();
        user.setEmail(email);
        user.setFullName("Old Name");
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto updatedUser = userService.updateUser(userDto);

        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);

        assertEquals(fullName, updatedUser.getFullName());
    }

}
