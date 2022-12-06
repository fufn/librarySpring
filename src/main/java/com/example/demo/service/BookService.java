package com.example.demo.service;

import com.example.demo.entities.Book;

public interface BookService {

    //@param book is the object of book that will be added to the database
    //@return the added book
    public Book addBook(Book book);

    //@param id - id number of the book that will be deleted
    public void deleteBook(Long id);

    //@param book is the object that will be updated in the database
    //@return the updated object
    public Book updateBook(Book book);

    //@param id is the number of the book that will be reserved
    //@return the updated book object
    public Book reserveBook(Long id);

}
