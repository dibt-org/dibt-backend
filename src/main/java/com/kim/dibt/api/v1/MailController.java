package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.email.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;


    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestParam("to") String to,
                                            @RequestParam("subject") String subject,
                                            @RequestParam("body") String body) {
        try {
            mailService.sendMail(to, subject, body);
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }

    @PostMapping(value = "/send-email-with-attachment", consumes = {"multipart/form-data"})
    public ResponseEntity<String> sendEmailWithAttachment(@RequestParam("to") String to,
                                                          @RequestParam("subject") String subject,
                                                          @RequestParam("body") String body,
                                                          @RequestPart("file") MultipartFile multipartFile) {
        try {
            File file = convertMultipartFileToFile(multipartFile);
            mailService.sendMultiMediaMail(to, subject, body, file);
            return ResponseEntity.ok("Email with attachment sent successfully");
        } catch (IOException | MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email with attachment");
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        return file;
    }
}
