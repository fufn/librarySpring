package com.example.demo.service.impl;

import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Library;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Library getLibrary(Long id) {
        return libraryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    @Override
    public Library addLibrary(Library library) {
        return libraryRepository.save(library);
    }

    @Override
    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }

    @Override
    public Library updateLibrary(Library library) {
        return libraryRepository.save(library);
    }

    @Override
    public List<Book> getBooks(Long id) {
        return libraryRepository.findById(id).orElse(null).getBooks();
    }

    @Override
    public BookDTO addBook(BookDTO book) {
        if (book != null) {
            Library library = libraryRepository.findById(book.getLibraryId()).orElse(null);
            Book newBook = new Book(book.getId(), book.getName(), book.getAuthor(), book.getDescription(), book.getYear(), book.getIsBooked());
            library.getBooks().add(newBook);
            libraryRepository.save(library);
            return book;
        }
        return null;
    }
}
