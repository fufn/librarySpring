package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * BookController - class representing rest controller.
 * Responsible for REST operations on book objects
 */
@RestController
@RequestMapping("/api/v0")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * @param book object that will be added to database(without id)
     * @return return the added book object with new id number
     */
    @PostMapping(value = "/books/add-book")
    public Book addBook(@RequestBody BookDTO book){
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
        bookService.deleteBook(book.getId());
    }
    /**
     * @return returns new updated version of book object
     * @param book - object that is needed to be updated in the database
     */
    @PutMapping(value = "/books/update-book")
    public Book updateBook(@RequestBody Book book){
        if (book != null) {
            return bookService.updateBook(book);
        }
        return null;
    }

    /**
     *
     * @param id - number of book that will be reserved
     * @return the updated book
     */
    @PutMapping(value = "/books/reserve-book/{id}")
    public Book reserveBook(@PathVariable(value = "id") Long id){
        return bookService.reserveBook(id);
    }



}
