package com.example.demo.dto.mapper;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import org.springframework.stereotype.Component;

/**
 * BookMapper is class responsible for transefring BookDTO object to Book and vice versa.
 */
@Component
public class BookMapper {
    /**
     * Transfers the entity to DTO version
     * @param book - initial book object
     * @param libraryId - ID of the library in which book will be stored
     * @return the DTO version of created book object
     */
    public BookDTO toDTO(Book book, Long libraryId){
        return new BookDTO(libraryId, book.getId(), book.getName(), book.getAuthor(), book.getDescription(), book.getYear(), book.getIsBooked());
    }

    /**
     * Transefrs the DTO to entity version.
     * @param bookDTO - DTO version of book which transfers to Book object
     * @return the Book entity vesion.
     */
    public Book toBook(BookDTO bookDTO){
        return new Book(bookDTO.getId(), bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getYear(), bookDTO.getIsBooked());
    }
}
