package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.LibraryDTO;
import com.example.demo.entity.Library;
import com.example.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LiibraryController - represents rest controller.
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
    public LibraryDTO addLibrary(@RequestBody LibraryDTO library){
        return libraryService.addLibrary(library);
    }
    /**
     *@param libraryDTO - DTO containing id number of library that is requested
     *@return return the requested library object from database
     */
    @GetMapping(value = "/libraries/{id}")
    public LibraryDTO getLibrary(@PathVariable(name = "id") Long id){
        return libraryService.getLibrary(id);
    }

    /**
     *@return return list of all libraries stored in database
     */
    @GetMapping(value = "/libraries")
    public List<LibraryDTO> getLibraries(){
        return libraryService.getLibraries();
    }

    /**
     *@param library - library that is requested to be deleted
     */
    @DeleteMapping(value = "/libraries")
    public void deleteLibrary(@RequestBody LibraryDTO library){
        libraryService.deleteLibrary(library);
    }

    /**
     *@param libraryDTO object that will be updated in database
     *@return return updated object of library
     */
    @PutMapping(value = "/libraries")
    public LibraryDTO updateLibrary(@RequestBody LibraryDTO libraryDTO){
        return libraryService.updateLibrary(libraryDTO);
    }

}
