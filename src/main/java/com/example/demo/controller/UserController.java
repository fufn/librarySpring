package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * UserController - represents rest controller.
 * Responsible for REST operations on user objects
 */
@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Logger logger = LogManager.getLogger(getClass());

    /**
     * @param userDto - has all information needed to register the user
     * @return the same Object but with id
     */
    @Operation(summary = "Registers users")
    @PostMapping(value = "/users")
    public UserDto registration(@Valid @RequestBody UserDto userDto) {
        logger.info("POST request to register user = {}", userDto);
        return userService.saveUser(userDto);
    }

    /**
     * @param pageable - object needed to take a peice of data from database
     * @return list of all users in form of Dto
     */
    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserDto> getUsers(@Valid @PageableDefault(value = 10, page = 0) Pageable pageable) {
        logger.info("GET request to get users. {}", pageable);
        return userService.findAllUsers(pageable);
    }

    /**
     * @param id - id of user that will be deleted
     */
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void deleteUser(@Valid @PathVariable(name = "id") Long id) {
        logger.info("DELETE request to delete user id = {}", id);
        userService.deleteById(id);
    }

    /**
     * @param userDto - has updated info about the user
     * @return updated info about the user
     */
    @PutMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto) {
        logger.info("PUT request to update user = {}", userDto);
        return userService.updateUser(userDto);
    }


}
