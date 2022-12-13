package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * BookController - class representing rest controller.
 * Responsible for REST operations on book objects
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0")
public class BookController {

    private final BookService bookService;

    /**
     * @param book object that will be added to database(without id)
     * @return return the added book object with new id number
     */
    @Operation(summary = "Adds book to the library")
    @PostMapping(value = "/books")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto addBook(@Valid @RequestBody BookDto book){
        return bookService.addBook(book);
    }

   /**
    * @param id is the number of book that is requested to be deleted
    */
    @Operation(summary = "Delete book from the library")
    @DeleteMapping(value = "/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBook(@Valid @PathVariable(name = "id") Long id){
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
        return bookService.updateBook(book);
    }

    /**
     *
     * @param bookDto - contains the id number of book that will be reserved and user email
     * @return the updated book
     */
    @Operation(summary = "Makes a reservation for book")
    @PutMapping(value = "/books/reserve")
    @PreAuthorize("hasRole('ROLE_USER')")
    public BookDto reserveBook(@Valid @RequestBody BookDto bookDto){
        return bookService.reserveBook(bookDto);
    }
}