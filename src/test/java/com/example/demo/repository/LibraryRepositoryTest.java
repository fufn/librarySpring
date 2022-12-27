package com.example.demo.repository;

import com.example.demo.entity.Library;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.demo.StaticCreator.getLibraryWithoutId;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Test
    public void testFindById(){
        //given
        Library given = getLibraryWithoutId();
        libraryRepository.save(given);
        //when
        Library result = libraryRepository.findById(given.getId()).get();
        //then
        assertEquals(given, result);
    }

    @Test
    public void testSave(){
        //given
        Library given = getLibraryWithoutId();
        //when
        Library result = libraryRepository.save(given);
        //then
        assertNotNull(result);
    }

    @Test
    public void testDelete(){
        //given
        Library given = getLibraryWithoutId();
        libraryRepository.save(given);
        libraryRepository.deleteById(given.getId());
        //when
        Library result = libraryRepository.findById(given.getId()).orElse(null);
        //then
        assertNull(result);
    }

    @Test
    public void testFindAll(){
        //given
        Library given = getLibraryWithoutId();
        Library given2 = getLibraryWithoutId();
        libraryRepository.save(given);
        libraryRepository.save(given2);
        //when
        List<Library> result = libraryRepository.findAll();
        //then
        assertEquals(result.size(), 2);
    }
}
