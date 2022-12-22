package com.example.demo.service;

import com.example.demo.controller.handler.BookNotFoundExceptionHandler;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.impl.BookMapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Library;
import com.example.demo.rabbit.RabbitMQSender;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testAddBook() {
        BookDto book = new BookDto();
        book.setAuthor("Author");
        book.setName("Book");
        book.setYear(2000);
        book.setDescription("Desription");
        book.setIsBooked(false);
        book.setLibraryId(1L);

        Book newBook = new Book();
        newBook.setIsBooked(book.getIsBooked());
        newBook.setAuthor(book.getAuthor());
        newBook.setYear(book.getYear());
        newBook.setDescription(book.getDescription());
        newBook.setLibrary(new Library(book.getLibraryId(), "library", "city", null));
        newBook.setName(book.getName());

        when(bookRepository.save(any(Book.class))).thenReturn(newBook);
        when(bookMapper.toDto(newBook)).thenReturn(book);
        when(bookMapper.toEntityCreation(book)).thenReturn(newBook);
        BookDto result = bookService.addBook(book);
        assertEquals(book, result);
        verify(bookRepository).save(bookMapper.toEntityCreation(book));
        verify(bookMapper).toDto(newBook);
    }

    @Test
    void testDeleteBook() {
        Long id = 1L;
        doNothing().when(bookRepository).deleteById(id);
        bookService.deleteBook(id);
        verify(bookRepository).deleteById(id);
    }

    @Test
    void testReserveBook_bookIsNotBooked() {
        // arrange
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(2L);
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsBooked(false);
        BookUser user = new BookUser();
        user.setId(bookDto.getUserId());
        book.setUser(user);
        when(bookRepository.findById(bookDto.getId())).thenReturn(Optional.of(book));
        when(userRepository.findById(bookDto.getUserId())).thenReturn(Optional.of(user));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        BookDto result = bookService.reserveBook(bookDto);
        assertEquals(bookDto, result);
        verify(bookRepository).findById(bookDto.getId());
        verify(userRepository).findById(bookDto.getUserId());
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void testReserveBook_bookIsBookedBySameUser() {
        // arrange
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(2L);
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsBooked(true);
        BookUser user = new BookUser();
        user.setId(bookDto.getUserId());
        book.setUser(user);
        when(bookRepository.findById(bookDto.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        BookDto result = bookService.reserveBook(bookDto);
        assertEquals(bookDto, result);
        verify(bookRepository).findById(bookDto.getId());
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }
    @Test
    void testGetByFilters_noResults() {
        // arrange
        BookDto bookDto = new BookDto();
        bookDto.setName("name");
        bookDto.setAuthor("author");
        bookDto.setIsBooked(false);
        when(bookRepository.getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked())).thenReturn(Collections.emptyList());
        List<BookDto> result = bookService.getByFilters(bookDto);
        assertTrue("", result.isEmpty());
        verify(bookRepository).getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked());
    }
}
