package com.enigmacamp.api.holasend.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendTokenToEmail(String to, String username, String secretActivationCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String html = EmailContent.resetPassword(to, username, secretActivationCode);

        helper.setTo(to);
        helper.setSubject("Reset Password");
        helper.setText(html, true);

        mailSender.send(message);

        return "Sent....";
    }
}
