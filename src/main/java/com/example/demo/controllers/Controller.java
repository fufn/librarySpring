package com.example.demo.controllers;

import com.example.demo.entities.Book;
import com.example.demo.entities.Library;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping(value = "/libraries")
    public List<Library> getLibraries(){
        return libraryRepository.findAll();
    }

    @PostMapping(value = "/libraries")
    public Library addLibrary(@RequestParam(value = "name") String name){
        Library library = new Library();
        library.setName(name);
        return libraryRepository.save(library);
    }

    @DeleteMapping(value = "/libraries")
    public void deleteLibrary(@RequestParam(value = "id") Long id){
        libraryRepository.deleteById(id);
    }

    @PutMapping(value = "/libraries")
    public Library updateLibrary(@RequestParam(value = "id") Long id,
                                 @RequestParam(value = "newName") String newName){
        Library library = libraryRepository.findById(id).orElse(null);
        if (library != null){
            library.setName(newName);
            libraryRepository.save(library);
            return library;
        }
        return library;
    }

    @GetMapping(value = "/books")
    public List<Book> getAllBooksInLibrary(@RequestParam(value = "library_id") Long id){
        Library library = libraryRepository.findById(id).orElse(null);
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
        Library library = libraryRepository.findById(id).orElse(null);
        if (library != null){
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setDescription(description);
            book.setYear(year);
            book.setIsBooked(false);
            library.getBooks().add(book);
            bookRepository.save(book);
            libraryRepository.save(library);
            return book;
        }
        return null;
    }

    @DeleteMapping(value = "/books")
    public void deleteBook(@RequestParam(value = "libraryId") Long libraryId,
                           @RequestParam(value = "bookId") Long bookId){
        Library library = libraryRepository.findById(libraryId).orElse(null);
        if (library != null) {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (book != null){
                for (Book b: library.getBooks()){
                    if (b.getId() == book.getId()) {
                        library.getBooks().remove(b);
                        break;
                    }
                }
                libraryRepository.save(library);
                bookRepository.deleteById(bookId);
            }
        }
    }

    @PutMapping(value = "/books")
    public Book updateBook(@RequestParam(value = "id") Long id,
                                 @RequestParam(value = "newName") String name,
                                 @RequestParam(value = "newAuthor") String author,
                                 @RequestParam(value = "newDescription") String description,
                                 @RequestParam(value = "newYear") Integer year){
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setName(name);
            book.setAuthor(author);
            book.setDescription(description);
            book.setYear(year);
            bookRepository.save(book);
            return book;
        }
        return null;
    }

    @PutMapping(value = "/books/reserve")
    public Book reserveBook(@RequestParam(value = "id") Long id){
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setIsBooked(!book.getIsBooked());
            bookRepository.save(book);
            return book;
        }
        return null;
    }



}
