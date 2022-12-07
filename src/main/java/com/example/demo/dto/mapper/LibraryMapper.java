package com.example.demo.dto.mapper;

import com.example.demo.dto.LibraryDTO;
import com.example.demo.entity.Library;
import org.springframework.stereotype.Component;

/**
 * LibraryMapper is responsible for transferring DTO to entity type and vice verca.
 */
@Component
public class LibraryMapper {

    /**
     * Transfers DTO to entity
     * @param libraryDTO the DTO of library
     * @return the entity type of object
     */
    public Library toLibrary(LibraryDTO libraryDTO){
        return new Library(libraryDTO.getId(), libraryDTO.getName(), libraryDTO.getBookList());
    }

    /**
     * Transfers entity to DTO
     * @param library
     * @return DTO type of library object
     */
    public LibraryDTO toDTO(Library library){
        return new LibraryDTO(library.getId(), library.getName(), library.getBookList());
    }
}
