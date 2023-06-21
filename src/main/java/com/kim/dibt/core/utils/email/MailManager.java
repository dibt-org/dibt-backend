package com.kim.dibt.core.utils.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.File;

@Service
@RequiredArgsConstructor
public class MailManager implements MailService {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailManager(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String to, String subject, String body)  {
        SimpleMailMessage message = createSimpleMailMessage(to, subject, body);
        sendEmail(message);
    }

    @Override
    public void sendMultiMediaMail(String to, String subject, String body, File file) throws MessagingException {
        MimeMessage message = createMimeMessage(to, subject, body);
        addAttachment(message, file);
        sendEmail(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = createHtmlMimeMessage(to, subject, body);
        sendEmail(message);
    }

    @Override
    public void sendMail(String to, String subject, String body, String from)  {
        SimpleMailMessage message = createSimpleMailMessage(to, subject, body);
        message.setFrom(from);
        sendEmail(message);
    }

    private void sendEmail(SimpleMailMessage message) throws MailException {
        javaMailSender.send(message);
    }

    private void sendEmail(MimeMessage message) throws MailException {
        javaMailSender.send(message);
    }

    private SimpleMailMessage createSimpleMailMessage(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(from);
        return message;
    }

    private MimeMessage createMimeMessage(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom(from);
        return message;
    }

    private MimeMessage createHtmlMimeMessage(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom(from);
        return message;
    }

    private void addAttachment(MimeMessage message, File file) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.addAttachment(file.getName(), file);
    }
}
