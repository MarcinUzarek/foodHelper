package com.example.foodhelper.service;

import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.TokenRepository;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final MailService mailService;
    private final static String URL = "http://localhost:8080/token?value=";

    public TokenService(TokenRepository tokenRepository, MailService mailService) {
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    public Token findToken(String value) {

        return tokenRepository.findByValue(value)
                .orElseThrow(() -> new IllegalArgumentException("no Token with this value were found"));
    }

    public void sendToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setUser(user);
        tokenRepository.save(token);

        try {
            mailService.sendMail(user.getEmail(), "Potwierdzaj to!", URL + tokenValue, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
