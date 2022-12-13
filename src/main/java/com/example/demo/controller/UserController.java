package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExist;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(name = "/api/v0")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users")
    public UserDto registration(@Valid @RequestBody UserDto userDto){
        User existingUser = userService.findByEmail(userDto.getEmail());

        if(existingUser != null){
            throw new UserAlreadyExist("There is alreade an account with the same email.");
        }

        return userService.saveUser(userDto);
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> users(@Valid @PageableDefault(value = 10, page = 0) Pageable pageable){
        return userService.findAllUsers(pageable);
    }

    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void users(@Valid @PathVariable(name = "id") Long id){
        userService.deleteById(id);
    }

    @PutMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }


}
