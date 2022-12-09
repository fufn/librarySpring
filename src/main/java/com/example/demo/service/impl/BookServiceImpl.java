package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.mapper.impl.BookMapper;
import com.example.demo.entity.Book;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto addBook(BookDto book) {
        Book newBook = bookRepository.save(bookMapper.toEntity(book));
        return bookMapper.toDto(newBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateBook(BookDto book) {
        Book newBook = bookRepository.save(bookMapper.toEntity(book));
        return bookMapper.toDto(newBook);
    }

    @Override
    public BookDto reserveBook(Long id) {
        Book bookToReserve = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No book with id " + id));
        bookToReserve.setIsBooked(!bookToReserve.getIsBooked());
        return bookMapper.toDto(bookRepository.save(bookToReserve));
    }
}
