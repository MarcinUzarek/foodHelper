package com.example.foodhelper.service;

import com.example.foodhelper.mail.MailService;
import com.example.foodhelper.mail.html_content_template.MailHtmlTemplate;
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


    private final static String URL = "http://localhost:8080/token";

    public TokenService(TokenRepository tokenRepository, MailService mailService) {
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    public Token findToken(String value) {
        return tokenRepository.findByValue(value)
                .orElseThrow(() -> new IllegalArgumentException("no Token with this value were found"));
    }

    public void sendToken(User user) {
        MailHtmlTemplate template = new MailHtmlTemplate();

        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setUser(user);
        tokenRepository.save(token);

        try {
            mailService.sendMail(user.getEmail(), "Activate account!", "Welcome",
                    template.getHtmlActivateTemplate(URL, tokenValue));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
