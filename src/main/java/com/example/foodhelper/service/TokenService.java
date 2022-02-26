package com.example.foodhelper.service;

import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;


    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public Token findToken(String value) {
        return tokenRepository.findByValue(value)
                .orElseThrow(() -> new IllegalArgumentException("no Token with this value were found"));
    }

    public String setTokenForUser(User user) {
        String tokenValue = generateToken();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setUser(user);
        tokenRepository.save(token);
        return tokenValue;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
