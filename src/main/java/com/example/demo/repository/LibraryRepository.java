package com.example.demo.repository;

import com.example.demo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface LibraryRepository extends JpaRepository<Library, Long>, JpaSpecificationExecutor<Library> {

}