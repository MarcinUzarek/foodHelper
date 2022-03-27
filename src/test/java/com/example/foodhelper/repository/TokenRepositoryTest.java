package com.example.foodhelper.repository;

import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TokenRepositoryTest {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    private void populateData() {
        User user = new User("username", "testemail@gmail.com", "password");
        userRepository.save(user);
        Token token = new Token();
        token.setValue("Q@wertyuiop");
        token.setUser(user);
        tokenRepository.save(token);
    }

    @BeforeEach
    void setUp() {
        populateData();
    }

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @Test
    void should_find_token_by_user_email_if_exists() {
        //given
        String email = "testemail@gmail.com";

        //when
        var token = tokenRepository.findByUser_Email(email).get();

        //then
        assertThat(token.getValue(), is("Q@wertyuiop"));
    }

    @Test
    void should_return_EmptyOptional_when_email_not_existing() {
        //given
        String email = "thisEmailDontExist@gmail.com";

        //when
        var token = tokenRepository.findByUser_Email(email);

        //then
        assertThat(token, is(Optional.empty()));
    }

    @Test
    void should_be_able_to_find_by_value() {
        //given
        String value = "Q@wertyuiop";

        //when
        var token = tokenRepository.findByValue(value).get();

        //then
        assertThat(token.getValue(), is("Q@wertyuiop"));
    }

    @Test
    void should_return_EmptyOptional_when_token_with_given_value_not_existing() {
        //given
        String value = "qwertyuiop";

        //when
        var token = tokenRepository.findByValue(value);

        //then
        assertThat(token, is(Optional.empty()));
    }


}