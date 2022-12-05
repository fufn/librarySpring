package com.example.demo.repositories;

import com.example.demo.entities.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BookRepo extends JpaRepository<Book, Long> {
}
