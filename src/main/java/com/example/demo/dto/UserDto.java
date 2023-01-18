package com.example.demo.dto;

import com.example.demo.entity.Book;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDTO - class representing data transfer object
 * Used in UserController and UserServiceImpl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    @NotEmpty
    private String fullName;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password should be empty")
    private String password;
    private List<Book> books;

}
