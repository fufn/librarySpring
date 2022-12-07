package com.example.demo.service;

import com.example.demo.dto.LibraryDTO;
import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Library;

import java.util.List;

/**
 * Service responsible for operations on library.
 */
public interface LibraryService {

    /**
     * @param id - id of library requested from database
     * @return return the library object from db
     */
    public LibraryDTO getLibrary(Long id);

    /**
     *@return returns list of all libraries
     */
    public List<LibraryDTO> getLibraries();

    /**
     *@param libraryDTO - the object of library that is needed to be saved to database
     *@return the object after successful save
     */
    public LibraryDTO addLibrary(LibraryDTO libraryDTO);

    /**
     *@param libraryDTO - the DTO containing id number of the library that will be deleted
     */
    public void deleteLibrary(LibraryDTO libraryDTO);

    /**
     *@param libraryDTO - the object of library that will be updated in the database
     *@return the updated object of library
     */
    public LibraryDTO updateLibrary(LibraryDTO libraryDTO);
}
