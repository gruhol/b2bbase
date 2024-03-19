package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.EmailData;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;
import pl.thinkdata.b2bbase.common.service.emailBuilder.EmailBuilder;
import pl.thinkdata.b2bbase.common.service.emailSenderService.EmailSenderService;
import pl.thinkdata.b2bbase.company.service.CompanyService;

import java.util.HashMap;
import java.util.Optional;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_FROM_GIVEN_TOKEN_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContactFormService {

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String MESSAGE = "message";
    public static final String BASEURL = "baseurl";
    private EmailBuilder emailBuilder;
    private EmailSenderService emailSenderService;
    private final BaseUrlService baseUrlService;
    private CompanyRepository companyRepository;

    public boolean sendEmail(EmailData emailData) {
        HashMap<String, String> values = new HashMap<>();
        values.put(NAME, emailData.getName());
        values.put(PHONE, emailData.getPhone());
        values.put(EMAIL, emailData.getEmail());
        values.put(MESSAGE, emailData.getMessage());
        values.put(BASEURL, baseUrlService.getUrl());
        //String emailSupplier = companyRepository.findById(emailData.getCompanyId()).ifPresent(.orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));

        String body = emailBuilder.prepareEmail("email-templates/contact_form", values);
        return false;
    }
}
