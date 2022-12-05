package com.example.demo.repositories;

import com.example.demo.entities.Library;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LibraryRepo extends JpaRepository<Library, Long> {
    Library findByName(String name);
}
