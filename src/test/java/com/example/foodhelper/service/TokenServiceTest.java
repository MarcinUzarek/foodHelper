package com.example.foodhelper.service;

import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;
    @Mock
    private TokenRepository tokenRepository;

    @Test
    void setTokenForUser() {
        //given
        Token gjesjsge = new Token();
        gjesjsge.setValue("aafkjkfjae");
        User user = new User("User", "user@gmail.com", "password");
        given(tokenRepository.save(any())).willReturn(gjesjsge);

        //when
        var token = tokenService.setTokenForUser(user);

        //then
        assertThat(token.getUser(), is(user));
    }

}