package com.example.demo.service.impl;

import com.example.demo.dto.BookDTO;
import com.example.demo.dto.BookMapper;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDTO addBook(BookDTO book) {
        System.out.println(bookMapper.toBook(book));
        Book newBook = bookRepository.save(bookMapper.toBook(book));
        bookRepository.addBook(newBook.getId(),book.getLibraryId());
        return bookMapper.toDTO(newBook, book.getLibraryId());
    }

    @Override
    public void deleteBook(BookDTO book) {

        Long bookId = book.getId();
        Book bookToDelete = bookRepository.findById(bookId).orElse(null);
        if (bookToDelete != null) {
            bookRepository.deleteBook(bookId);
            bookRepository.deleteById(bookId);
        }
    }

    @Override
    public BookDTO updateBook(BookDTO book) {
        if (book != null) {
            Book newBook = bookRepository.save(bookMapper.toBook(book));
            return bookMapper.toDTO(newBook, book.getLibraryId());
        }
        return null;
    }

    @Override
    public BookDTO reserveBook(BookDTO book) {
        Book bookToReserve = bookRepository.findById(book.getId()).orElse(null);
        if (book != null) {
            bookToReserve.setIsBooked(!bookToReserve.getIsBooked());
            Book newBook = bookRepository.save(bookToReserve);
            return bookMapper.toDTO(newBook, book.getLibraryId());
        }
        return null;
    }
}
