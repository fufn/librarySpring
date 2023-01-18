package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.ReserveDto;
import com.example.demo.dto.mapper.impl.BookMapper;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookController - class representing rest controller.
 * Responsible for REST operations on book objects
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final Logger logger = LogManager.getLogger(getClass());

    /**
     * @param book object that will be added to database(without id)
     * @return return the added book object with new id number
     */
    @Operation(summary = "Adds book to the library")
    @PostMapping(value = "/books")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto addBook(@Valid @RequestBody BookDto book) {
        logger.info("POST request to add book = {} ", book);
        return bookService.addBook(bookMapper.toEntityCreation(book));
    }

    /**
     * @param id is the number of book that is requested to be deleted
     */
    @Operation(summary = "Delete book from the library")
    @DeleteMapping(value = "/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@Valid @PathVariable(name = "id") Long id) {
        logger.info("DELETE request to delete book id = {}", id);
        bookService.deleteBook(id);
    }

    /**
     * @param book - object that is needed to be updated in the database
     * @return returns new updated version of book object
     */
    @Operation(summary = "Updates the book")
    @PutMapping(value = "/books")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto updateBook(@Valid @RequestBody BookDto book) {
        logger.info("PUT request to update book = {}", book);
        return bookService.updateBook(bookMapper.toEntity(book));
    }

    /**
     * @param reserveDto - contains the id number of book that will be reserved and user email
     */
    @Operation(summary = "Makes a reservation for book")
    @PutMapping(value = "/books/reserve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void reserveBook(@Valid @RequestBody ReserveDto reserveDto) {
        logger.info("PUT request to reserve book = {}", reserveDto);
        bookService.reserveBookRabbitMQ(reserveDto);
    }

    /**
     * @param filterDto contains the required filters
     */
    @GetMapping(value = "/books")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @Operation(summary = "searches for books with certain author")
    public Page<BookDto> getBookByFilters(@Valid @RequestBody FilterDto filterDto, @RequestBody Pageable pageable) {
        logger.info("GET request to get books by filters = {}", filterDto);
        return bookService.getByFilters(filterDto, pageable);
    }
}