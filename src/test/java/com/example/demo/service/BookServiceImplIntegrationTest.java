package com.example.demo.service;

import com.example.demo.controller.handler.BookNotFoundExceptionHandler;
import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Library;
import com.example.demo.entity.Role;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class BookServiceImplIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryRepository libraryRepository;

    private BookUser user;

    @BeforeEach
    void setUp() {
        Role role = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(role);
        user = BookUser.builder()
                .fullName("John")
                .email("john@example.com")
                .roles(List.of(role))
                .password("password")
                .build();
        userRepository.save(user);
    }

    @Test
    void addBook_success() {
        // given
        BookDto bookDto = new BookDto();
        bookDto.setName("Test Book");
        bookDto.setDescription("DEscription");
        bookDto.setIsBooked(false);
        bookDto.setAuthor("Author");
        bookDto.setYear(2000);
        // when
        BookDto result = bookService.addBook(bookDto);

        // then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Book", result.getName());
    }

    @Test
    void deleteBook_success() {
        // given
        Book book = new Book();
        book.setName("Test Book");
        book.setLibrary(Library.builder()
                        .city("city")
                                .name("library").build());
        book.setDescription("DEscription");
        book.setIsBooked(false);
        book.setAuthor("Author");
        book.setYear(2000);
        bookRepository.save(book);

        // when
        bookService.deleteBook(book.getId());

        // then
        assertFalse(bookRepository.existsById(book.getId()));
    }

    @Test
    void reserveBook_bookNotFound() {
        // given
        BookDto bookDto = new BookDto();
        bookDto.setId(0L);
        bookDto.setUserId(user.getId());
        // when
        assertThrows(BookNotFoundExceptionHandler.class, () -> bookService.reserveBook(bookDto));
    }

    @Test
    void reserveBook_bookAlreadyReserved() {
        // given
        Book book = new Book();
        book.setName("Test Book");
        book.setLibrary(Library.builder()
                .city("city")
                .name("library").build());
        book.setDescription("DEscription");
        book.setIsBooked(true);
        book.setAuthor("Author");
        book.setYear(2000);
        book.setUser(BookUser.builder().id(4L).build());
        bookRepository.save(book);

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setUserId(user.getId());

        // when
        BookDto result = bookService.reserveBook(bookDto);

        // then
        assertTrue(result.getIsBooked());
        assertNotEquals(user.getId(), result.getUserId());
    }

    @Test
    void reserveBook_success() {
        // given
        Book book = new Book();
        book.setName("Test Book");
        book.setDescription("DEscription");
        book.setIsBooked(false);
        book.setAuthor("Author");
        book.setYear(2000);
        bookRepository.save(book);

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setUserId(user.getId());

        // when
        BookDto result = bookService.reserveBook(bookDto);

        // then
        assertTrue(result.getIsBooked());
        assertEquals(user.getId(), result.getUserId());
    }

    @Test
    void getByFilters_success() {
        // given
        Library library = Library.builder()
                .city("city")
                .name("library").build();
        libraryRepository.save(library);
        Book book1 = new Book();
        book1.setName("Test Book 1");
        book1.setDescription("DEscription");
        book1.setLibrary(library);
        book1.setIsBooked(true);
        book1.setAuthor("Author");
        book1.setYear(2000);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setName("Test Book 1");
        book2.setLibrary(library);
        book2.setDescription("DEscription");
        book2.setIsBooked(true);
        book2.setAuthor("Author");
        book2.setYear(2000);
        bookRepository.save(book2);

        BookDto bookDto = new BookDto();
        bookDto.setName("Test Book 1");
        bookDto.setIsBooked(true);
        bookDto.setAuthor("Author");
        bookDto.setYear(2000);

        // when
        List<BookDto> result = bookService.getByFilters(bookDto);

        // then
        assertEquals(2, result.size());
    }
}