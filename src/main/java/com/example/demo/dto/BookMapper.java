package com.example.demo.dto;

import com.example.demo.entity.Book;
import org.springframework.stereotype.Component;

/**
 * BookMapper is class responsible for transefring BookDTO object to Book and vice versa.
 */
@Component
public class BookMapper {
    public BookDTO toDTO(Book book, Long libraryId){
        return new BookDTO(libraryId, book.getId(), book.getName(), book.getAuthor(), book.getDescription(), book.getYear(), book.getIsBooked());
    }
    public Book toBook(BookDTO bookDTO){
        return new Book(bookDTO.getId(), bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getYear(), bookDTO.getIsBooked());
    }
}
