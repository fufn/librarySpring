package com.example.demo;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Library;
import com.example.demo.entity.Role;
import java.util.List;

public class StaticCreator {

    public static Book getBookWithoutId(){
        return Book.builder()
                .author("author")
                .name("book")
                .year(2000)
                .description("description")
                .isBooked(false)
                .build();
    }

    public static BookDto getBookDtoWithoutId(){
        return BookDto.builder()
                .author("author")
                .name("book")
                .year(2000)
                .description("description")
                .isBooked(false)
                .build();
    }

    public static Library getLibraryWithoutId(){
        return Library.builder()
                .name("Library")
                .city("City")
                .build();
    }

    public static BookUser getBookUserWithoutId(){
        return BookUser.builder()
                .fullName("user")
                .email("user@user.com")
                .password("password")
                .books(null)
                .build();
    }

    public static Role getRoleWithoutId(){
        return Role.builder()
                .name("ROLE_USER")
                .build();
    }
}
