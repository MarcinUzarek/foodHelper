package com.example.foodhelper.service;

import com.example.foodhelper.model.Role;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.TokenRepository;
import com.example.foodhelper.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;
    private final MailService mailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role("ROLE_USER"));
        userRepository.save(user);
        sendToken(user);
    }

    private void sendToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setUser(user);
        tokenRepository.save(token);
        String url = "http://localhost:8080/token?value=" + tokenValue;

        try {
            mailService.sendMail(user.getEmail(), "Potwierdzaj to!", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }


}