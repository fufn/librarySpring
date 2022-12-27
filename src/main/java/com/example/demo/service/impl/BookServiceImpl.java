package com.example.demo.service.impl;

import com.example.demo.controller.handler.BookAlreadyReservedExceptionHandler;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.impl.BookMapper;
import com.example.demo.entity.Book;
import com.example.demo.controller.handler.BookNotFoundExceptionHandler;
import com.example.demo.rabbit.RabbitMQSender;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final RabbitMQSender rabbitMQSender;
    private final Logger logger = LogManager.getLogger(getClass());
    private final static String BOOK_ALREADY_RESERVED = "this book is already reserved";
    private final static String BOOK_NOT_FOUND = "There is no such book to reserve";

    @Override
    public BookDto addBook(BookDto book) {
        logger.debug("Adding new book to repository");
        Book newBook = bookRepository.save(bookMapper.toEntityCreation(book));
        logger.debug("Returning the book with id");
        return bookMapper.toDto(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        logger.debug("Deleting book with id = " + id);
        bookRepository.deleteById(id);
        logger.debug("Deleted the book");
    }

    @Override
    public BookDto updateBook(BookDto book) {
        logger.debug("Updating the book");
        Book newBook = bookRepository.save(bookMapper.toEntity(book));
        logger.debug("Updated book - " + newBook);
        return bookMapper.toDto(newBook);
    }

    @Override
    public BookDto reserveBook(BookDto bookDto) {
        logger.debug("Reserving book. " + bookDto);
        Book bookToReserve = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new BookNotFoundExceptionHandler("No book with id " + bookDto.getId()));
        if (!bookToReserve.getIsBooked()) {
            logger.debug("Book was not reserved");
            bookToReserve.setIsBooked(!bookToReserve.getIsBooked());
            bookToReserve.setUser(userRepository.findById(bookDto.getUserId()).orElseThrow());
            logger.debug("Book reserved by user with id =" + bookDto.getUserId());
        } else if (bookToReserve.getIsBooked() && bookToReserve.getUser().getId() == bookDto.getUserId()) {
            logger.debug("Book is unreserved");
            bookToReserve.setIsBooked(!bookToReserve.getIsBooked());
        } else {
            throw new BookAlreadyReservedExceptionHandler(BOOK_ALREADY_RESERVED);
        }
        return bookMapper.toDto(bookRepository.save(bookToReserve));
    }

    @Override
    public void reserveBookRabbitMQ(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new BookNotFoundExceptionHandler(BOOK_NOT_FOUND));
        logger.debug("Sending bookDto to RabbitMQ queue. " + bookDto);
        rabbitMQSender.sendBookDto(bookDto);
        logger.debug("BookDto was sent.");
    }

    @Override
    public List<BookDto> getByFilters(BookDto bookDto) {
        logger.debug("Trying to get books by filters");
        List<Book> books = bookRepository.getByFilters(bookDto.getName(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getIsBooked());
        if (books == null) {
            throw new BookNotFoundExceptionHandler("There is no books with required filters");
        }
        logger.debug("Found books.");
        return bookMapper.listToDto(books);
    }
}
