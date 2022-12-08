package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookMapper implements Mapper<BookDto, Book>{

    private Long libraryId;

    public BookDto toDto(Book book){
//       TODO BookDto.builder().author()
        return new BookDto(libraryId, book.getId(), book.getName(), book.getAuthor(), book.getDescription(), book.getYear(), book.getIsBooked());
    }

    public Book toEntity(BookDto bookDTO){
        return new Book(bookDTO.getId(), bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getYear(), bookDTO.getIsBooked());
    }

}
