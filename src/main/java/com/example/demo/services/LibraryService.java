package com.example.demo.services;

import com.example.demo.entities.Book;

import java.io.FileNotFoundException;
import java.util.List;

public interface LibraryService {
    public Book addBook() throws FileNotFoundException;
    public List<Book> getBooks();
    public Book updateBook() throws FileNotFoundException;
    public void deleteBook() throws FileNotFoundException;
    public void makeBooking() throws FileNotFoundException;
    public void unbook() throws FileNotFoundException;
}
