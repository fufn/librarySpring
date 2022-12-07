package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    //@param takes book object that will be added to database(without id)
    //@return return the added book object with new id number
    @PostMapping(value = "/books/add-book")
    public Book addBook(@RequestBody Book book){
        if (book != null) {
            return bookService.addBook(book);
        }
        return null;
    }

    //@oaram takes id of book that is requested to be deleted
    @DeleteMapping(value = "/books/delete-book/{id}")
    public void deleteBook(@PathVariable(value = "id") Long bookId){
        bookService.deleteBook(bookId);
    }

    //@param book - object that is needed to be updated in the database
    //@return returns new updated version of book object
    @PutMapping(value = "/books/update-book")
    public Book updateBook(@RequestBody Book book){
        if (book != null) {
            return bookService.updateBook(book);
        }
        return null;
    }

    //@param id - number of book that will be reserved
    //@return return the updated book
    @PutMapping(value = "/books/reserve-book/{id}")
    public Book reserveBook(@PathVariable(value = "id") Long id){
        return bookService.reserveBook(id);
    }



}
