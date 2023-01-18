package com.example.demo.controller;


import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.StaticCreator.getBookDtoWithoutId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ContextConfiguration
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "admin@adminovich.com", password = "asdasd", authorities = {"ADMIN"})
    public void addBookTest() throws Exception {

        BookDto bookDto = getBookDtoWithoutId();

        when(bookService.addBook(any(Book.class))).thenReturn(bookDto);

        mockMvc.perform(post("/books")
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(bookDto.getName()));
    }

}