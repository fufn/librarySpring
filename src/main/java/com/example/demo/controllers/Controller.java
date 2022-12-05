package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.entities.Library;
import com.example.demo.repositories.BookRepo;
import com.example.demo.repositories.LibraryRepo;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private LibraryRepo libraryRepo;

    @GetMapping(value = "/libraries")
    public List<Library> getLibraries(){
        return libraryRepo.findAll();
    }

    @PostMapping(value = "/libraries")
    public Library addLibrary(@RequestParam(value = "name") String name){
        Library library = new Library();
        library.setName(name);
        return libraryRepo.save(library);
    }

    @DeleteMapping(value = "/libraries")
    public void deleteLibrary(@RequestParam(value = "id") Long id){
        libraryRepo.deleteById(id);
    }

    @PutMapping(value = "/libraries")
    public Library updateLibrary(@RequestParam(value = "id") Long id,
                                 @RequestParam(value = "newName") String newName){
        Library library = libraryRepo.findById(id).orElse(null);
        if (library != null){
            library.setName(newName);
            libraryRepo.save(library);
            return library;
        }
        return library;
    }

    @GetMapping(value = "/books")
    public List<Book> getAllBooksInLibrary(@RequestParam(value = "library_id") Long id){
        Library library = libraryRepo.findById(id).orElse(null);
        if (library != null) {
            return library.getBooks();
        }
        return null;
    }

    @PostMapping(value = "/books")
    public Book addBook(@RequestParam(value = "libraryId") Long id,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "author") String author,
                        @RequestParam(value = "description") String description,
                        @RequestParam(value = "year") Integer year){
        Library library = libraryRepo.findById(id).orElse(null);
        if (library != null){
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setDescription(description);
            book.setYear(year);
            book.setIsBooked(false);
            library.getBooks().add(book);
            bookRepo.save(book);
            libraryRepo.save(library);
            return book;
        }
        return null;
    }

    @DeleteMapping(value = "/books")
    public void deleteBook(@RequestParam(value = "libraryId") Long libraryId,
                           @RequestParam(value = "bookId") Long bookId){
        Library library = libraryRepo.findById(libraryId).orElse(null);
        if (library != null) {
            Book book = bookRepo.findById(bookId).orElse(null);
            if (book != null){
                for (Book b: library.getBooks()){
                    if (b.getId() == book.getId()) {
                        library.getBooks().remove(b);
                        break;
                    }
                }
                libraryRepo.save(library);
                bookRepo.deleteById(bookId);
            }
        }
    }

    @PutMapping(value = "/books")
    public Book updateBook(@RequestParam(value = "id") Long id,
                                 @RequestParam(value = "newName") String name,
                                 @RequestParam(value = "newAuthor") String author,
                                 @RequestParam(value = "newDescription") String description,
                                 @RequestParam(value = "newYear") Integer year){
        Book book = bookRepo.findById(id).orElse(null);
        if (book != null) {
            book.setName(name);
            book.setAuthor(author);
            book.setDescription(description);
            book.setYear(year);
            bookRepo.save(book);
            return book;
        }
        return null;
    }

    @PutMapping(value = "/books/reserve")
    public Book updateLibrary(@RequestParam(value = "id") Long id){
        Book book = bookRepo.findById(id).orElse(null);
        if (book != null) {
            book.setIsBooked(!book.getIsBooked());
            bookRepo.save(book);
            return book;
        }
        return null;
    }



}
