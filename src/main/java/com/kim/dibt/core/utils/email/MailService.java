package com.kim.dibt.core.utils.email;

import jakarta.mail.MessagingException;

import java.io.File;

public interface MailService {
    String sendMail(String to);
    String sendMultiMediaMail(String to) throws MessagingException;
}
