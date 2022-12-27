package com.example.demo.repository;

import com.example.demo.entity.BookUser;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.demo.StaticCreator.getBookUserWithoutId;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById(){
        //given
        BookUser given = getBookUserWithoutId();
        userRepository.save(given);
        //when
        BookUser result = userRepository.findById(given.getId()).get();
        //then
        assertEquals(given, result);
    }

    @Test
    public void testSave(){
        //given
        BookUser given = getBookUserWithoutId();
        //when
        BookUser result = userRepository.save(given);
        //then
        assertNotNull(result);
    }

    @Test
    public void testDelete(){
        //given
        BookUser given = getBookUserWithoutId();
        userRepository.save(given);
        userRepository.deleteById(given.getId());
        //when
        BookUser result = userRepository.findById(given.getId()).orElse(null);
        //then
        assertNull(result);
    }

    @Test
    public void testFindAll(){
        //given
        BookUser given = getBookUserWithoutId();
        BookUser given2 = getBookUserWithoutId();
        given2.setEmail("test@test.com");
        userRepository.save(given);
        userRepository.save(given2);
        //when
        List<BookUser> result = userRepository.findAll();
        //then
        assertEquals(result.size(), 2);
    }

    @Test
    public void testFindByEmail(){
        //given
        BookUser given = getBookUserWithoutId();
        userRepository.save(given);
        //when
        BookUser result = userRepository.findByEmail(given.getEmail());
        //then
        assertEquals(result, given);
    }
}
