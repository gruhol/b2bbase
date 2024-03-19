package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.EmailData;

@Service
@RequiredArgsConstructor
public class ContactFormService {

    public boolean sendEmailByContactForm(EmailData emailData) {
        return false;
    }
}
