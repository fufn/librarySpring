package com.example.demo.service.impl;

import com.example.demo.controller.handler.BookException;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.FilterDto;
import com.example.demo.dto.ReserveDto;
import com.example.demo.dto.mapper.impl.BookMapper;
import com.example.demo.entity.Book;
import com.example.demo.rabbit.RabbitMQSender;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

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
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BookDto addBook(Book book) {
        logger.debug("Adding new book to repository");
        Book newBook = bookRepository.save(book);
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
    public BookDto updateBook(Book book) {
        logger.debug("Updating the book");
        Book newBook = bookRepository.save(book);
        logger.debug("Updated book - " + newBook);
        return bookMapper.toDto(newBook);
    }

    @Override
    public BookDto reserveBook(Book book) {
        logger.debug("Reserving book. " + book);
        Book bookToReserve = bookRepository.findById(book.getId()).orElseThrow(() -> new BookException("No book with id " + book.getId()));
        if (!bookToReserve.getIsBooked()) {
            logger.debug("Book was not reserved");
            bookToReserve.setIsBooked(!bookToReserve.getIsBooked());
            bookToReserve.setUser(userRepository.findById(book.getUser().getId()).orElseThrow());
            logger.debug("Book reserved by user with id =" + book.getUser().getId());
        } else if (bookToReserve.getIsBooked() && bookToReserve.getUser().getId() == book.getUser().getId()) {
            logger.debug("Book is unreserved");
            bookToReserve.setIsBooked(!bookToReserve.getIsBooked());
        } else {
            throw new BookException(BOOK_ALREADY_RESERVED);
        }
        return bookMapper.toDto(bookRepository.save(bookToReserve));
    }

    @Override
    public void reserveBookRabbitMQ(ReserveDto reserveDto) {
        Book bookCheck = bookRepository.findById(reserveDto.getBookId()).orElseThrow(() -> new BookException(BOOK_NOT_FOUND));
        logger.debug("Sending bookDto to RabbitMQ queue. " + bookCheck);
        rabbitMQSender.sendBookDto(bookCheck);
        logger.debug("BookDto was sent.");
    }

    @Override
    public Page<BookDto> getByFilters(FilterDto filterDto, Pageable pageable) {
        logger.debug("Trying to get books by filters");

        StringBuilder queryBuilder = new StringBuilder();
        String sqlSelect = "SELECT b.is_booked as booked, b.id as id, b.description as description, b.year as year, b.author as author, b.name as name FROM book b WHERE";
        queryBuilder.append(sqlSelect);

        if (filterDto.getYear() != null) {
            queryBuilder.append(" year = ").append(filterDto.getYear());
        }
        if (filterDto.getName() != null) {
            if (filterDto.getYear() != null) queryBuilder.append(" AND");
            queryBuilder.append(" name = '").append(filterDto.getName()).append("'");
        }
        if (filterDto.getAuthor() != null) {
            if (filterDto.getName() != null) queryBuilder.append(" AND");
            queryBuilder.append(" author = '").append(filterDto.getAuthor()).append("'");
        }

        if (pageable != null) {
            queryBuilder.append(" limit ").append(pageable.getPageSize()).append(" offset ").append(pageable.getOffset());
        }

        List<Book> books = jdbcTemplate.query(queryBuilder.toString(), new BookMapperSql());

        if (books == null) {
            throw new BookException("There is no books with required filters");
        }

        logger.debug("Found books.");
        List<BookDto> bookDtos = bookMapper.filterToDto(books);
        return new PageImpl<>(bookDtos);
    }

    private class BookMapperSql implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .year(rs.getInt("year"))
                    .id(rs.getLong("id"))
                    .description(rs.getString("description"))
                    .author(rs.getString("author"))
                    .name(rs.getString("name"))
                    .isBooked(rs.getBoolean("booked"))
                    .build();
        }
    }
}
