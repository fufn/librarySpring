package com.example.demo.dto;

import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
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
    private Long id;
    private String name;
    private String author;
    private String description;
    private Integer year;
    private Boolean isBooked;
    private Long libraryId;

}
