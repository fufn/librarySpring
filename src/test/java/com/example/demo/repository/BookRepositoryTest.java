package com.example.demo.repository;

import com.example.demo.entity.Book;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.demo.StaticCreator.getBookWithoutId;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindById(){
        //given
        Book given = getBookWithoutId();
        bookRepository.save(given);
        //when
        Book result = bookRepository.findById(given.getId()).get();
        //then
        assertEquals(given, result);
    }

    @Test
    public void testSave(){
        //given
        Book given = getBookWithoutId();
        //when
        Book result = bookRepository.save(given);
        System.out.println(result.getId());
        //then
        assertNotNull(result);
    }

    @Test
    public void testDelete(){
        //given
        Book given = getBookWithoutId();
        bookRepository.save(given);
        bookRepository.deleteById(given.getId());
        //when
        Book result = bookRepository.findById(given.getId()).orElse(null);
        //then
        assertNull(result);
    }

    @Test
    public void testFindAll(){
        //given
        Book given = getBookWithoutId();
        Book given2 = getBookWithoutId();
        bookRepository.save(given);
        bookRepository.save(given2);
        //when
        List<Book> result = bookRepository.findAll();
        //then
        assertEquals(result.size(), 2);
    }
}
