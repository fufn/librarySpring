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
     *@param bookDto contains the number of book that will be reserved and user email
     *@return the updated book object
     */
    public BookDto reserveBook(BookDto bookDto);

    /**
     * Sends bookDto to RabbitMQSender
     * @param bookDto - has book id and user id to make a reservation
     */
    public void reserveBookRabbitMQ(BookDto bookDto);

}
