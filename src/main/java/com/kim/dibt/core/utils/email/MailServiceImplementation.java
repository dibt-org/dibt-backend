package com.kim.dibt.core.utils.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailServiceImplementation implements MailService {
    private JavaMailSender mailSender;

    @Autowired
    public MailServiceImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String sendMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("destek.dibt@gmail.com");
        message.setSubject("DİBT Destek");
        message.setTo(to);
        message.setText("Selamlar Kutsal Bey DİBT ile alaklı bir desteğe ihtiyacınız var mı?");
        mailSender.send(message);
        return "Mail gönderildi";
    }

    @Override
    public String sendMultiMediaMail(String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
        message.setFrom("destek.dibt@gmail.com");
        message.setSubject("DİBT Destek");
        message.setTo(to);
        message.setText("Selamlar Kutsal Bey DİBT ile alaklı bir desteğe ihtiyacınız var mı?");
        FileSystemResource file = new FileSystemResource(new File("/Users/kufutsal/Desktop/deneme.png"));
        message.addAttachment("Attachment.png",file);
        mailSender.send(mimeMessage);
        return "Multi medya mail gönderildi";
    }
}
