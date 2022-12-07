package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;

/**
 * Service responsible for operations on book object.
 */
public interface BookService {

    /**
     * @param bookDTO object of book instance
     * @return returns the book which was added to Database
     */
    public BookDTO addBook(BookDTO bookDTO);

    /**
     * @param bookDTO - the book that will be deleted
     */
    public void deleteBook(BookDTO bookDTO);

    /**
     *@param bookDTO is the object that will be updated in the database
     *@return the updated object
     */
    public BookDTO updateBook(BookDTO bookDTO);

    /**
     *@param bookDTO is the book that will be reserved
     *@return the updated book object
     */
    public BookDTO reserveBook(BookDTO bookDTO);

}
