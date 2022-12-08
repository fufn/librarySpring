package com.example.demo.controller;

import com.example.demo.dto.LibraryDto;
import com.example.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
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
    @PostMapping(value = "/libraries")
    public LibraryDto addLibrary(@RequestBody LibraryDto library){
        return libraryService.addLibrary(library);
    }
    /**
     *@param id - DTO containing id number of library that is requested
     *@return return the requested library object from database
     */
    @GetMapping(value = "/libraries/{id}")
    public LibraryDto getLibrary(@PathVariable(name = "id") Long id){
        return libraryService.getLibrary(id);
    }

    /**
     *@return return list of all libraries stored in database
     */
    @GetMapping(value = "/libraries")
    public List<LibraryDto> getLibraries(){
        return libraryService.getLibraries();
    }

    /**
     *@param id - the number of library that is requested to be deleted
     */
    @DeleteMapping(value = "/libraries/{id}")
    public void deleteLibrary(@PathVariable(name = "id") Long id){
        libraryService.deleteLibrary(id);
    }

    /**
     *@param libraryDTO object that will be updated in database
     *@return return updated object of library
     */
    @PutMapping(value = "/libraries")
    public LibraryDto updateLibrary(@RequestBody LibraryDto libraryDTO){
        return libraryService.updateLibrary(libraryDTO);
    }

}
