package com.example.demo.service;

import com.example.demo.dto.FilterDto;
import com.example.demo.dto.LibraryDto;
import com.example.demo.entity.Library;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service responsible for operations on library.
 */
public interface LibraryService {

    /**
     * @param id id of library requested from database
     * @return return the library object from db
     */
    LibraryDto getLibrary(Long id);

    /**
     * @param pageable - pageable object that needed to return slice of data
     * @return returns list of all libraries
     */
    Page<LibraryDto> getLibraries(Pageable pageable);

    /**
     * @param library the object of library that is needed to be saved to database
     * @return the object after successful save
     */
    LibraryDto addLibrary(Library library);

    /**
     * @param id - the id number of the library that will be deleted
     */
    void deleteLibrary(Long id);

    /**
     * @param library the object of library that will be updated in the database
     * @return the updated object of library
     */
    LibraryDto updateLibrary(Library library);

    List<LibraryDto> getByFilters(FilterDto filterDto);
}
