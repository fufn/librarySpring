package com.example.demo.service;

import com.example.demo.controller.handler.BookException;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.impl.BookMapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Library;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.BookServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void givenBook_whenAddBook_thenSuccess() {
        //given
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
        //when
        BookDto result = bookService.addBook(newBook);
        //then
        assertEquals(book, result);
        verify(bookRepository).save(bookMapper.toEntityCreation(book));
        verify(bookMapper).toDto(newBook);
    }

    @Test
    void givenId_whenDeleteBook_thenSuccess() {
        //given
        Long id = 1L;
        doNothing().when(bookRepository).deleteById(id);
        //when
        bookService.deleteBook(id);
        //then
        verify(bookRepository).deleteById(id);
    }

    @Test
    void givenBookDto_whenReserveBook_thenSuccess() {
        //given
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
        //when
        BookDto result = bookService.reserveBook(book);
        //then
        assertEquals(true, book.getIsBooked());

        verify(bookRepository).findById(bookDto.getId());
        verify(userRepository).findById(bookDto.getUserId());
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    public void givenBookDto_whenReserveBook_thenBookUnreserved() {
        //given
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(2L);

        BookUser user = new BookUser();
        user.setId(2L);

        Book book = new Book();
        book.setId(1L);
        book.setIsBooked(true);
        book.setUser(user);

        when(bookRepository.findById(bookDto.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);
        //when
        BookDto result = bookService.reserveBook(book);

        //then
        assertEquals(false, book.getIsBooked());
    }

    @Test
    public void givenBookDto_whenReserveReservedBook_thenThrowException() {
        //given
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(2L);
        BookUser user = new BookUser();
        user.setId(3L);
        Book book = new Book();
        book.setId(1L);
        book.setIsBooked(true);
        book.setUser(user);
        when(bookRepository.findById(bookDto.getId())).thenReturn(Optional.of(book));

        // when and then
        assertThrows(BookException.class, () -> bookService.reserveBook(book));
    }

    @Test
    void givenBadFilters_whenFindByFilters_thenNoResult() {
        // given
        BookDto bookDto = new BookDto();
        bookDto.setName("name");
        bookDto.setAuthor("author");
        bookDto.setIsBooked(false);
        bookDto.setYear(2000);
        //when(bookRepository.getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked())).thenReturn(null);
        //when and then
        //assertThrows(BookNotFoundExceptionHandler.class, () -> bookService.getByFilters(bookDto));
        //verify(bookRepository).getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked());
    }

    @Test
    void givenGoodFilters_whenFindByFilters_thenNoResult() {
        // given
        BookDto bookDto = new BookDto();
        bookDto.setName("name");
        bookDto.setAuthor("author");
        bookDto.setIsBooked(false);
        bookDto.setYear(2000);

        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setIsBooked(bookDto.getIsBooked());
        book.setYear(bookDto.getYear());

        //when(bookRepository.getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked())).thenReturn(List.of(book));
        when(bookMapper.listToDto(List.of(book))).thenReturn(List.of(bookDto));
        //when and then
        //List<BookDto> result = bookService.getByFilters(bookDto);
        //assertEquals(List.of(bookDto), result);
        //verify(bookRepository).getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked());
    }
}
