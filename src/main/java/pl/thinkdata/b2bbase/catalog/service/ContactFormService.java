package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.EmailData;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;
import pl.thinkdata.b2bbase.common.service.emailBuilder.EmailBuilder;
import pl.thinkdata.b2bbase.common.service.emailSenderService.EmailSenderService;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import java.util.HashMap;

import static pl.thinkdata.b2bbase.common.tool.CommonDictionary.MESSAGE_FROM;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.EMAIL_SUPPLIER_NOT_FOUND_BY_COMPANY_ID;

@Service
@RequiredArgsConstructor
public class ContactFormService {

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String MESSAGE = "message";
    public static final String BASEURL = "baseurl";

    private final EmailBuilder emailBuilder;
    private final EmailSenderService emailSenderService;
    private final BaseUrlService baseUrlService;
    private final MessageGenerator messageGenerator;
    private final CompanyRepository companyRepository;

    public boolean sendEmail(EmailData emailData) {
        HashMap<String, String> values = new HashMap<>();
        values.put(NAME, emailData.getName());
        values.put(PHONE, emailData.getPhone());
        values.put(EMAIL, emailData.getEmail());
        values.put(MESSAGE, emailData.getMessage());
        values.put(BASEURL, baseUrlService.getUrl());
        String emailSupplier = companyRepository.findById(emailData.getCompanyId())
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(EMAIL_SUPPLIER_NOT_FOUND_BY_COMPANY_ID)))
                .getEmail();

        String body = emailBuilder.prepareEmail("email-templates/contact_form", values);
        String url = generateURL(emailData);
        return emailSenderService.sendEmail(emailSupplier, messageGenerator.get(MESSAGE_FROM), body, url);
    }

    /*
    * Method to generate string values from EmailData to test sendEmail method in mock function.
     */
    private String generateURL(EmailData emailData) {
        return emailData.getName() + "|" +
                emailData.getPhone() + "|" +
                emailData.getEmail() + "|" +
                emailData.getMessage() + "|";
    }
}
