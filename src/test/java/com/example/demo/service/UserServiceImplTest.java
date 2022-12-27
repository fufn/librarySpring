package com.example.demo.service;

import com.example.demo.controller.handler.UserAlreadyExistHandler;
import com.example.demo.dto.UserDto;
import com.example.demo.dto.mapper.impl.UserMapper;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

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
    public void givenUser_whenSaveUser_thenSuccess() {
        //given
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
        when(userRepository.save(any(BookUser.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        //when
        UserDto savedUser = userService.saveUser(userDto);
        //then
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(user);
        assertEquals(userDto, savedUser);
    }

    @Test
    public void givenUser_whenSaveUser_thenUserAlreadyExist() {
        //given
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
        //when
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        //then
        assertThrows(UserAlreadyExistHandler.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void givenEmail_whenFindByEmail_thenSuccess() {
        //given
        String email = "test@example.com";
        BookUser user = new BookUser();
        user.setEmail(email);
        user.setFullName("Test User");
        user.setPassword("password");

        when(userRepository.findByEmail(email)).thenReturn(user);
        //when
        BookUser foundUser = userService.findByEmail(email);
        //then
        verify(userRepository, times(1)).findByEmail(email);
        assertEquals(user, foundUser);
    }

    @Test
    public void givenNewUser_whenUpdateUser_thenSuccess() {
        //given
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
        //when
        UserDto updatedUser = userService.updateUser(userDto);

        //then
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
        assertEquals(fullName, updatedUser.getFullName());
    }

    @Test
    public void givenId_whenDeleteById_thenDeleted() {
        doNothing().when(userRepository).deleteById(anyLong());
        //when
        userService.deleteById(1L);
        //then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void givenPageable_whenFindAll_thenSuccess() {
        //given
        Pageable pageable = PageRequest.of(0, 20);

        BookUser user1 = BookUser.builder()
                .id(1L)
                .email("test@mail.com")
                .fullName("Test Test")
                .password("pswd")
                .build();
        BookUser user2 = BookUser.builder()
                .id(2L)
                .email("test2@mail.com")
                .fullName("Test Test")
                .password("pswd")
                .build();

        UserDto userDto1 = UserDto.builder()
                .id(user1.getId())
                .email(user1.getEmail())
                .fullName(user1.getFullName())
                .password(null)
                .build();

        UserDto userDto2 = UserDto.builder()
                .id(user2.getId())
                .email(user2.getEmail())
                .fullName(user2.getFullName())
                .password(null)
                .build();

        List<BookUser> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        Page<BookUser> users = new PageImpl<>(list);

        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);
        when(userRepository.findAll(pageable)).thenReturn(users);

        //when
        Page<UserDto> result = userService.findAllUsers(pageable);

        //then
        assertNotNull(result);
        assertEquals(2, result.getSize());

    }

}
