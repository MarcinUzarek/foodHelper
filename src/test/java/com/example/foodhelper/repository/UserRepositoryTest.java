package com.example.foodhelper.repository;

import com.example.foodhelper.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private void populateUserTable() {
        User user1 = new User("First", "first@email.com", "first password");
        User user2 = new User("Second", "second@email.com", "second password");
        User user3 = new User("Third", "third@email.com", "third password");
        User user4 = new User("Fourth", "fourth@email.com", "fourth password");
        User user5 = new User("Fifth", "fith@email.com", "fifth password");
        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);
        userRepository.saveAll(users);
    }

    @BeforeEach
    void setUp() {
        populateUserTable();
    }

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