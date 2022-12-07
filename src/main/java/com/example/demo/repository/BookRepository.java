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
    @Query  (value = "DELETE FROM library_books WHERE books_id = ?1", nativeQuery = true)
    void deleteBook(Long bookId);
}
