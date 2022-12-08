package com.example.demo.dto;

import com.example.demo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookDTO - class representing data transfer object
 * Used in BookController and BookServiceImpl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

    private Long libraryId;
    private Long id;
    private String name;
    private String author;
    private String description;
    private Integer year;
    private Boolean isBooked;

    public BookDto(Long libraryId, Book book){
        this.libraryId = libraryId;
        this.id = book.getId();
        this.author = book.getAuthor();
        this.name = book.getName();
        this.description = book.getDescription();
        this.year = book.getYear();
        this.isBooked = book.getIsBooked();
    }

}
