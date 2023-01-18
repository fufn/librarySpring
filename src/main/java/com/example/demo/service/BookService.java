package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.ReserveDto;
import com.example.demo.entity.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service responsible for operations on book object.
 */
public interface BookService {

    /**
     * @param book object of book instance
     * @return returns the book which was added to Database
     */
    BookDto addBook(Book book);

    /**
     * @param id - the book that will be deleted
     */
    void deleteBook(Long id);

    /**
     * @param book is the object that will be updated in the database
     * @return the updated object
     */
    BookDto updateBook(Book book);

    /**
     * @param book contains the number of book that will be reserved and user email
     * @return the updated book object
     */
    BookDto reserveBook(Book book);

    /**
     * Sends bookDto to RabbitMQSender
     *
     * @param reserveDto - has book id and user id to make a reservation
     */
    void reserveBookRabbitMQ(ReserveDto reserveDto);

    /**
     * Find all books by author name
     *
     * @param filterDto - required parameters to search books
     * @return list of BookDtos
     */
    Page<BookDto> getByFilters(FilterDto filterDto, Pageable pageable);
}
