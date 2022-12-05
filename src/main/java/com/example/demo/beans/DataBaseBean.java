package com.example.demo.beans;

import com.example.demo.entities.Book;
import com.example.demo.entities.Library;
import com.example.demo.repositories.BookRepo;
import com.example.demo.repositories.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DataBaseBean {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private LibraryRepo libraryRepo;

    public Book addBook(Book book){
        return bookRepo.save(book);
    }

    public Book saveBook(Book book){
        return bookRepo.save(book);
    }

    public void deleteBook(Long id){
        bookRepo.deleteById(id);
    }

    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    public Library addLibrary(Library library){
        return libraryRepo.save(library);
    }

    public Library saveBook(Library library){
        return libraryRepo.save(library);
    }

    public void deleteLibrary(Long id){
        libraryRepo.deleteById(id);
    }

    public List<Library> getlibraries(){
        return libraryRepo.findAll();
    }

    public Library getLibrary(String name){
        return libraryRepo.findByName(name);
    }


}
