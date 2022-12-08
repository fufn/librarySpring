package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Data
public class BookMapper implements Mapper<BookDto, Book>{

    public BookDto toDto(Book book){
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .year(book.getYear())
                .isBooked(book.getIsBooked())
                .libraryId(book.getLibrary().getId())
                .build();
    }

    public Book toEntity(BookDto bookDTO){
        return Book.builder()
                .id(bookDTO.getId())
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .description(bookDTO.getDescription())
                .year(bookDTO.getYear())
                .isBooked(bookDTO.getIsBooked())
                .library(Library.builder()
                        .id(bookDTO.getLibraryId())
                        .build())
                .build();
    }

    @Override
    public List<BookDto> listToDto(List<Book> entities) {
        if (entities != null) {
            return entities.stream().map(this::toDto).toList();
        }
        return null;
    }

    @Override
    public List<Book> listToEntity(List<BookDto> dtos) {
        if (dtos != null){
            return dtos.stream().map(this::toEntity).toList();
        }
        return null;
    }

}
