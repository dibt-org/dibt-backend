package com.kim.dibt.api.v1;

import com.kim.dibt.core.utils.email.MailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/text")
    public ResponseEntity <String> sendTextMail(@RequestBody @Valid String to) {
        return ResponseEntity.ok(mailService.sendMail(to));
    }

    @PostMapping("/multi-media")
    public ResponseEntity <String> sendMultiMediaMail(@RequestBody @Valid String to) throws MessagingException {
        return ResponseEntity.ok(mailService.sendMultiMediaMail(to));
    }
}
