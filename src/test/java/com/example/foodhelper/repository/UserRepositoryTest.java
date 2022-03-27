package com.example.foodhelper.repository;

import com.example.foodhelper.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findAll() {
        var all = userRepository.findAll();
        System.out.println(all);
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
        System.out.println("hdhfh");
    }

    @Test
    void testFindAll() {
    }

    @Test
    void existsByEmail() {
    }
}