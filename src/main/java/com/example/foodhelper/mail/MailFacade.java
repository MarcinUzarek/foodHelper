package com.example.foodhelper.mail;

import com.example.foodhelper.mail.html_content_template.MailHtmlTemplate;
import com.example.foodhelper.model.Token;
import com.example.foodhelper.model.User;
import com.example.foodhelper.repository.TokenRepository;
import com.example.foodhelper.service.TokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class MailFacade {

    private final MailService mailService;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    private final MailHtmlTemplate template = new MailHtmlTemplate();

    private final static String VERIFICATION_URL = "http://localhost:8080/token";
    private final static String FORGOT_PASS_URL = "http://localhost:8080/new-pass";


    public MailFacade(MailService mailService, TokenService tokenService, TokenRepository tokenRepository) {
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
    }

    public void sendActivationEmail(User user) {
        String tokenValue = tokenService.setTokenForUser(user);

        try {
            mailService.sendMail(user.getEmail(), "Activate account!", "Welcome",
                    template.getHtmlActivateTemplate(VERIFICATION_URL, tokenValue));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Token sendLinkToResetPass(String email) {
        Token token = tokenRepository.findByUser_Email(email).orElseThrow(
                () -> new IllegalArgumentException("No account with such email")
        );
        try {
            mailService.sendMail(email, "Change Password", "false",
                    template.getHtmlPassReset(FORGOT_PASS_URL, token.getValue()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return token;
    }
}
