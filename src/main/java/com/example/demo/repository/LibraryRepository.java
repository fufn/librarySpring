package com.example.demo.repository;

import com.example.demo.entity.Library;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface LibraryRepository extends JpaRepository<Library, Long> {

}