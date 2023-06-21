package com.kim.dibt.core.utils.email;

import jakarta.mail.MessagingException;

import java.io.File;


public interface MailService {
    void sendMail(String to, String subject, String body) throws MessagingException;
    void sendMultiMediaMail(String to, String subject, String body, File file) throws MessagingException;
    void sendHtmlMail(String to, String subject, String body) throws MessagingException;
    void sendMail(String to, String subject, String body, String from) throws MessagingException;
}
