package com.example.demo.service;

import com.example.demo.dto.LibraryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service responsible for operations on library.
 */
public interface LibraryService {

    /**
     * @param id - id of library requested from database
     * @return return the library object from db
     */
    LibraryDto getLibrary(Long id);

    /**
     * @param pageable - pageable object that needed to return slice of data
     *@return returns list of all libraries
     */
    Page<LibraryDto> getLibraries(Pageable pageable);

    /**
     *@param libraryDTO - the object of library that is needed to be saved to database
     *@return the object after successful save
     */
    LibraryDto addLibrary(LibraryDto libraryDTO);

    /**
     *@param id - the id number of the library that will be deleted
     */
    void deleteLibrary(Long id);

    /**
     *@param libraryDTO - the object of library that will be updated in the database
     *@return the updated object of library
     */
    LibraryDto updateLibrary(LibraryDto libraryDTO);

    List<LibraryDto> getByFilters(LibraryDto libraryDto);
}
