package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * BookController - class representing rest controller.
 * Responsible for REST operations on book objects
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0")
public class BookController {

    private final BookService bookService;
    private final Logger logger = LogManager.getLogger(getClass());

    /**
     * @param book object that will be added to database(without id)
     * @return return the added book object with new id number
     */
    @Operation(summary = "Adds book to the library")
    @PostMapping(value = "/books")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto addBook(@Valid @RequestBody BookDto book){
        logger.info("POST request to add book = {} ", book);
        return bookService.addBook(book);
    }

   /**
    * @param id is the number of book that is requested to be deleted
    */
    @Operation(summary = "Delete book from the library")
    @DeleteMapping(value = "/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@Valid @PathVariable(name = "id") Long id){
        logger.info("DELETE request to delete book id = {}", id);
        bookService.deleteBook(id);
    }

    /**
     * @return returns new updated version of book object
     * @param book - object that is needed to be updated in the database
     */
    @Operation(summary = "Updates the book")
    @PutMapping(value = "/books")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto updateBook(@Valid @RequestBody BookDto book){
        logger.info("PUT request to update book = {}", book);
        return bookService.updateBook(book);
    }

    /**
     * @param bookDto - contains the id number of book that will be reserved and user email
     */
    @Operation(summary = "Makes a reservation for book")
    @PutMapping(value = "/books/reserve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void reserveBook(@Valid @RequestBody BookDto bookDto){
        logger.info("PUT request to reserve book = {}", bookDto);
        bookService.reserveBook(bookDto);
    }
}