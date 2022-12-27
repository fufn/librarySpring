package com.example.demo.repository;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM book WHERE book.is_booked = :isBooked) as bkd WHERE bkd.year = :year) as yr WHERE yr.author = :author   ) as ath WHERE ath.name = :name", nativeQuery = true)
    List<Book> getByFilters(@Param("name") String name, @Param("author") String author, @Param("year") Integer year, @Param("isBooked") Boolean isBooked);

}
