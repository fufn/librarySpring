package com.example.demo.controller;

import com.example.demo.dto.FilterDto;
import com.example.demo.dto.LibraryDto;
import com.example.demo.dto.mapper.impl.LibraryMapper;
import com.example.demo.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LibraryController - represents rest controller.
 * Responsible for REST operations on library objects
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v0")
public class LibraryController {

    private final LibraryService libraryService;
    private final LibraryMapper libraryMapper;
    private final Logger logger = LogManager.getLogger(getClass());

    /**
     * @param library obejct that will be added to database (without id)
     * @return returns the added object with its id number
     */
    @Operation(summary = "Add library to database")
    @PostMapping(value = "/libraries")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public LibraryDto addLibrary(@Valid @RequestBody LibraryDto library) {
        logger.info("POST request to add library = {} ", library);
        return libraryService.addLibrary(libraryMapper.toEntity(library));
    }

    /**
     * @param id - DTO containing id number of library that is requested
     * @return return the requested library object from database
     */
    @Operation(summary = "Get a library by id")
    @GetMapping(value = "/libraries/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public LibraryDto getLibrary(@Valid @PathVariable(name = "id") Long id) {
        logger.info("GET request to get library id = {}", id);
        return libraryService.getLibrary(id);
    }

    /**
     * @return return list of all libraries stored in database
     */
    @Operation(summary = "Get all libraries with pageable")
    @GetMapping(value = "/libraries")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Page<LibraryDto> getLibraries(@Valid @PageableDefault(value = 10, page = 0) Pageable pageable) {
        logger.info("GET request to get libraries. {}", pageable);
        return libraryService.getLibraries(pageable);
    }

    /**
     * @param id - the number of library that is requested to be deleted
     */
    @Operation(summary = "Get a library with id")
    @DeleteMapping(value = "/libraries/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteLibrary(@Valid @PathVariable(name = "id") Long id) {
        logger.info("DELETE request to delete library id = {}", id);
        libraryService.deleteLibrary(id);
    }

    /**
     * @param libraryDTO object that will be updated in database
     * @return return updated object of library
     */
    @Operation(summary = "Updates a library")
    @PutMapping(value = "/libraries")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public LibraryDto updateLibrary(@Valid @RequestBody LibraryDto libraryDTO) {
        logger.info("PUT request to update library = {}", libraryDTO);
        return libraryService.updateLibrary(libraryMapper.toEntity(libraryDTO));
    }

    @Operation(summary = "Get libraries by filter")
    @GetMapping(value = "/libraries/filter")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<LibraryDto> getByFilet(@Valid @RequestBody FilterDto filterDto) {
        logger.info("GET request to get libraries with filter = {}", filterDto);
        return libraryService.getByFilters(filterDto);
    }

}
