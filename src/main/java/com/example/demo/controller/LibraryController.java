package com.example.demo.controller;

import com.example.demo.dto.LibraryDto;
import com.example.demo.error.ErrorMessage;
import com.example.demo.exception.LibraryNotFoundException;
import com.example.demo.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LibraryController - represents rest controller.
 * Responsible for REST operations on library objects
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0")
public class LibraryController {

    private final LibraryService libraryService;

    /**
     *@param library obejct that will be added to database (without id)
     *@return returns the added object with its id number
     */
    @Operation(summary = "Add library to database")
    @PostMapping(value = "/libraries")
    public LibraryDto addLibrary(@Valid @RequestBody LibraryDto library){
        return libraryService.addLibrary(library);
    }
    /**
     *@param id - DTO containing id number of library that is requested
     *@return return the requested library object from database
     */
    @Operation(summary = "Get a library by id")
    @GetMapping(value = "/libraries/{id}")
    public LibraryDto getLibrary(@Valid @PathVariable(name = "id") Long id){
        return libraryService.getLibrary(id);
    }

    /**
     *@return return list of all libraries stored in database
     */
    @Operation(summary = "Get all libraries with pageable")
    @GetMapping(value = "/libraries")
    public List<LibraryDto> getLibraries(@Valid @PageableDefault(value = 10, page = 0) Pageable pageable){
        return libraryService.getLibraries(pageable);
    }

    /**
     *@param id - the number of library that is requested to be deleted
     */
    @Operation(summary = "Get a library with id")
    @DeleteMapping(value = "/libraries/{id}")
    public void deleteLibrary(@Valid @PathVariable(name = "id") Long id){
        libraryService.deleteLibrary(id);
    }

    /**
     *@param libraryDTO object that will be updated in database
     *@return return updated object of library
     */
    @Operation(summary = "Updates a library")
    @PutMapping(value = "/libraries")
    public LibraryDto updateLibrary(@Valid @RequestBody LibraryDto libraryDTO){
        return libraryService.updateLibrary(libraryDTO);
    }
}
