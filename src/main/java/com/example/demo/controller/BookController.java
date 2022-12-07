package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import com.example.demo.service.impl.BookServiceImpl;
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
    public BookDTO addBook(@RequestBody BookDTO book){
        if (book != null) {
            return bookService.addBook(book);
        }
        return null;
    }

   /**
    * @param book that is requested to be deleted
    */
    @DeleteMapping(value = "/books")
    public void deleteBook(@RequestBody BookDTO book){
        bookService.deleteBook(book);
    }
    /**
     * @return returns new updated version of book object
     * @param book - object that is needed to be updated in the database
     */
    @PutMapping(value = "/books/update-book")
    public BookDTO updateBook(@RequestBody BookDTO book){
        if (book != null) {
            return bookService.updateBook(book);
        }
        return null;
    }

    /**
     *
     * @param book - book that will be reserved
     * @return the updated book
     */
    @PutMapping(value = "/books/reserve-book/{id}")
    public BookDTO reserveBook(@RequestBody BookDTO book){
        return bookService.reserveBook(book);
    }



}
