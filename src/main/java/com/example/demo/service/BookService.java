package com.example.demo.service;

import com.example.demo.dto.BookDto;

/**
 * Service responsible for operations on book object.
 */
public interface BookService {

    /**
     * @param bookDTO object of book instance
     * @return returns the book which was added to Database
     */
    public BookDto addBook(BookDto bookDTO);

    /**
     * @param id - the book that will be deleted
     */
    public void deleteBook(Long id);

    /**
     *@param bookDTO is the object that will be updated in the database
     *@return the updated object
     */
    public BookDto updateBook(BookDto bookDTO);

    /**
     *@param id is the number of book that will be reserved
     *@return the updated book object
     */
    public BookDto reserveBook(Long id);

}
