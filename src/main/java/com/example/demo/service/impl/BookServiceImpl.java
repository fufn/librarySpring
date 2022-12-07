package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {

        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            bookRepository.deleteBook(bookId);
            bookRepository.deleteById(bookId);
        }
    }

    @Override
    public Book updateBook(Book book) {
        if (book != null) {
            bookRepository.save(book);
            return book;
        }
        return null;
    }

    @Override
    public Book reserveBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setIsBooked(!book.getIsBooked());
            bookRepository.save(book);
            return book;
        }
        return null;
    }
}
