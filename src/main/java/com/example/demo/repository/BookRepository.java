package com.example.demo.repository;

import com.example.demo.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Query  (value = "INSERT INTO library_books (library_id, books_id) VALUES (?2, ?1)", nativeQuery = true)
    void addBook(Long bookId, Long libraryId);
}
