package pl.thinkdata.b2bbase.catalog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.catalog.dto.EmailData;
import pl.thinkdata.b2bbase.catalog.service.ContactFormService;

@RestController
@RequestMapping("/sendEmail")
@RequiredArgsConstructor
public class SendEmailController {

    private ContactFormService contactFormService;

    @PostMapping("/contactForm")
    public boolean sendEmail(@RequestBody @Valid EmailData emailData) {
        return contactFormService.sendEmail(emailData);
    }
}
