package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.BookUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service responsible for operations on users.
 */
public interface UserService {
    /**
     *
     * @param userDto - Dto of User that will be registered in system.
     * @return the same UserDto but with generated id.
     */
    UserDto saveUser(UserDto userDto);

    /**
     *
     * @param email - the email of the user that need to be searched
     * @return the User in database
     */
    BookUser findByEmail(String email);

    /**
     *
     * @param pageable - pageable object that needed to return slice of data
     * @return the page of all users.
     */
    Page<UserDto> findAllUsers(Pageable pageable);

    /**
     * Deletes user with certain id.
     * @param id - id of user that will be deleted.
     */
    void deleteById(Long id);

    /**
     * Updates the user information.
     * @param userDto - has all updated information about the user.
     * @return updated UserDto
     */
    UserDto updateUser(UserDto userDto);


}
