package com.example.demo.repository;

import com.example.demo.entity.Role;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.demo.StaticCreator.getRoleWithoutId;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindById(){
        //given
        Role given = getRoleWithoutId();
        roleRepository.save(given);
        //when
        Role result = roleRepository.findById(given.getId()).get();
        //then
        assertEquals(given, result);
    }

    @Test
    public void testSave(){
        //given
        Role given = getRoleWithoutId();
        //when
        Role result = roleRepository.save(given);
        //then
        assertNotNull(result);
    }

    @Test
    public void testDelete(){
        //given
        Role given = getRoleWithoutId();
        roleRepository.save(given);
        roleRepository.deleteById(given.getId());
        //when
        Role result = roleRepository.findById(given.getId()).orElse(null);
        //then
        assertNull(result);
    }

    @Test
    public void testFindAll(){
        //given
        Role given = getRoleWithoutId();
        Role given2 = getRoleWithoutId();
        given2.setName("ROLE_ADMIN");
        roleRepository.save(given);
        roleRepository.save(given2);
        //when
        List<Role> result = roleRepository.findAll();
        //then
        assertEquals(result.size(), 2);
    }

    @Test
    public void findByName(){
        //given
        Role role = getRoleWithoutId();
        roleRepository.save(role);
        //when
        Role result = roleRepository.findByName(role.getName());
        //then
        assertEquals(role, result);
    }
}
