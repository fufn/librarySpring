package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookUser;
import com.example.demo.entity.Library;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookMapper implements Mapper<BookDto, Book> {

    @Override
    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .year(book.getYear())
                .isBooked(book.getIsBooked())
                .libraryId(book.getLibrary().getId())
                .userId(book.getUser() == null ? null : book.getUser().getId())
                .build();
    }

    public List<BookDto> filterToDto(List<Book> books) {
        return books.stream()
                .map(book -> {
                    return BookDto.builder()
                            .description(book.getDescription())
                            .id(book.getId())
                            .isBooked(book.getIsBooked())
                            .year(book.getYear())
                            .name(book.getName())
                            .author(book.getAuthor())
                            .build();
                }).collect(Collectors.toList());
    }

    public Book toEntityCreation(BookDto bookDTO) {
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
    public Book toEntity(BookDto bookDTO) {
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
                .user(BookUser.builder()
                        .id(bookDTO.getUserId())
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
        if (dtos != null) {
            return dtos.stream().map(this::toEntity).toList();
        }
        return null;
    }

}
