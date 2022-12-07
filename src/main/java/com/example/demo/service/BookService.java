package com.example.demo.service;

import com.example.demo.entity.Book;

/**
 * Service responsible for operations on book object.
 */
public interface BookService {

    //@param takes object of book instance
    //@return returns the book which was added to Database
    public Book addBook(Book book);

    //@param id - id number of the book that will be deleted
    public void deleteBook(Long bookId);

    //@param book is the object that will be updated in the database
    //@return the updated object
    public Book updateBook(Book book);

    //@param id is the number of the book that will be reserved
    //@return the updated book object
    public Book reserveBook(Long id);

//    //@param id is the number of the book that is requested
//    //@return the book object by the given id
//    public Book getBook(Long id);

}
