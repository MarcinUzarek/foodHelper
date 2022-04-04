package com.example.foodhelper.mail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @InjectMocks
    MailService mailService;

    @Spy
    JavaMailSender javaMailSender = new JavaMailSenderImpl();

    @Test()
    void should_sent_email() throws MessagingException {
        //given
        doNothing().when(javaMailSender)
                .send(any(MimeMessage.class));

        //when
        mailService.sendMail("random@email.com",
                "subject", "text", "htmlContent");

        //then
        verify(javaMailSender, times(1))
                .send(any(MimeMessage.class));
    }
}