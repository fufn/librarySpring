package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Library;

import java.util.List;

public interface LibraryService {

    //@param takes the library id number
    //@return return the library object from db
    public Library getLibrary(Long id);

    //@return returns list of all libraries
    public List<Library> getLibraries();

    //@param library - the object of library that is needed to be saved to database
    //@return the object after successful save
    public Library addLibrary(Library library);

    //@param id - the id number of the library that will be deleted
    public void deleteLibrary(Long id);

    //@param library - the object of library that will be updated in the database
    //@return the updated object of library
    public Library updateLibrary(Library library);

    //@param id - id number of the library which list of books is required
    //@return the list of books in requested library
    public List<Book> getBooks(Long id);

    //@param book is the object of book that will be added to the database
    //@return the added book
    public BookDTO addBook(BookDTO book);


}
