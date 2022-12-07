package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Library;
import com.example.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    //@param takes bookDTO object
    //@return return the added bookDTO object
    @PostMapping(value = "/libraries/add-book")
    public BookDTO addBook(@RequestBody BookDTO book){
        return libraryService.addBook(book);
    }

    //@param takes Library obejct that will be added to database (without id)
    //@return returns the added object with its id number
    @PostMapping(value = "/libraries/add-library")
    public Library addLibrary(@RequestBody Library library){
        return libraryService.addLibrary(library);
    }

    //@param id - id number of library that is requested
    //@return return the requested library object from database
    @GetMapping(value = "/libraries/{id}")
    public Library getLibrary(@PathVariable(name = "id") Long id){
        return libraryService.getLibrary(id);
    }

    //@return return list of all libraries stored in database
    @GetMapping(value = "/libraries")
    public List<Library> getLibraries(){
        return libraryService.getLibraries();
    }

    //@param id - id number of library that is requested to be deleted
    @DeleteMapping(value = "/libraries/delete-library/{id}")
    public void deleteLibrary(@PathVariable Long id){
        libraryService.deleteLibrary(id);
    }

    //@param requests new library object that will be updated in database
    //@return return updated object of library
    @PutMapping(value = "/libraries/update-library")
    public Library updateLibrary(@RequestBody Library library){
        return libraryService.updateLibrary(library);
    }

}
