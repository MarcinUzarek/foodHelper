package com.example.foodhelper.repository;

import com.example.foodhelper.TestDataSample;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest implements TestDataSample {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        var users = getSampleDataForUsers();
        userRepository.saveAll(users);    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void should_be_able_to_find_all_users() {
        //when
        var users = userRepository.findAll();
        //then
        assertThat(users.size(), is(5));
    }

    @Test
    void should_find_user_by_email_if_in_database() {
        //given
        String email = "third@email.com";

        //when
        var result = userRepository.existsByEmail(email);

        //then
        assertThat(result, is(true));
    }

    @Test
    void should_not_find_user_by_email_if_not_in_database() {
        //given
        String email = "random@email.com";

        //when
        var result = userRepository.existsByEmail(email);

        //then
        assertThat(result, is(false));
    }
}