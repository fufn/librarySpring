package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @PostMapping(value = "/books")
    public BookDto addBook(@Valid @RequestBody BookDto book){
        return bookService.addBook(book);
    }

   /**
    * @param id is the number of book that is requested to be deleted
    */
    @DeleteMapping(value = "/books/{id}")
    public void deleteBook(@Valid @PathVariable(name = "id") Long id){
        bookService.deleteBook(id);
    }

    /**
     * @return returns new updated version of book object
     * @param book - object that is needed to be updated in the database
     */
    @PutMapping(value = "/books")
    public BookDto updateBook(@Valid @RequestBody BookDto book){
        return bookService.updateBook(book);
    }

    /**
     *
     * @param id - the id number of book that will be reserved
     * @return the updated book
     */
    @PutMapping(value = "/books/reserve/{id}")
    public BookDto reserveBook(@Valid @PathVariable(name = "id") Long id){
        return bookService.reserveBook(id);
    }
}