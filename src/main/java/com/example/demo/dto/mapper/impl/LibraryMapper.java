package com.example.demo.dto.mapper.impl;

import com.example.demo.dto.LibraryDto;
import com.example.demo.dto.mapper.Mapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LibraryMapper implements Mapper<LibraryDto, Library> {

    private final BookMapper bookMapper;

    public Library toEntity(LibraryDto libraryDTO) {
        List<Book> books = bookMapper.listToEntity(libraryDTO.getBooks());

        return Library.builder()
                .id(libraryDTO.getId())
                .name(libraryDTO.getName())
                .city(libraryDTO.getCity())
                .books(books)
                .build();
    }

    @Override
    public List<LibraryDto> listToDto(List<Library> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    @Override
    public List<Library> listToEntity(List<LibraryDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    public LibraryDto toDto(Library library) {
        return LibraryDto.builder()
                .id(library.getId())
                .name(library.getName())
                .books(bookMapper.listToDto(library.getBooks()))
                .city(library.getCity())
                .build();
    }
}
