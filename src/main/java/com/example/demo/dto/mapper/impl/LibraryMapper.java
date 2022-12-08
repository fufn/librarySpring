package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.LibraryDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryMapper implements Mapper<LibraryDto, Library> {

    public Library toEntity(LibraryDto libraryDTO){
        Library library = new Library();
        library.setId(libraryDTO.getId());
        library.setName(libraryDTO.getName());
        List<Book> books = libraryDTO.getBooks().stream().map(bookDto -> new Book(bookDto.getId(), bookDto.getName(), bookDto.getAuthor(), bookDto.getDescription(), bookDto.getYear(), bookDto.getIsBooked())).toList();
        library.setBooks(books);
        return library;
    }

    public LibraryDto toDto(Library library){
        LibraryDto dto = new LibraryDto();
        dto.setName(library.getName());
        dto.setId(library.getId());
        List<BookDto> bookDtos = library.getBooks().stream().map(book -> new BookDto(library.getId(), book)).toList();
        dto.setBooks(bookDtos);
        return dto;
    }
}
