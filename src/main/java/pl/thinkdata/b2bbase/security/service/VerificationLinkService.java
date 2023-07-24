package pl.thinkdata.b2bbase.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.thinkdata.b2bbase.common.service.emailservice.MyEmailService;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLink;
import pl.thinkdata.b2bbase.security.repository.VerificationLinkRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationLinkService {

    private final VerificationLinkRepository verificationLinkRepository;
    private final MyEmailService myEmailService;
    private final MessageGenerator messageGenerator;
    private final TemplateEngine templateEngine;
    private final HttpServletRequest request;

    private static final String URL = "url";
    private static final String VERIFY = "/verify/";
    private static final String BASEURL = "baseurl";
    private static final String CONFIRM_YOUR_REGISTRATION = "confirm.your.registration";

    public void createVerificationLinkAndSendEmail(User user) {
        VerificationLink link = new VerificationLink();
        link.setUser(user);
        verificationLinkRepository.save(link);
        Context context = new Context();
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String url = baseUrl + VERIFY + link.getToken();
        context.setVariable(URL, url);
        context.setVariable(BASEURL, baseUrl);
        String body = templateEngine.process("email-templates/registration-confirmation", context);
        myEmailService.sendEmail(user.getUsername(), messageGenerator.get(CONFIRM_YOUR_REGISTRATION), body, url);
    }

    public boolean checkVerificationLink(String token) {
        return verificationLinkRepository.findByToken(token)
                .filter(verificationLink -> !verificationLink.getIsConsumed())
                .filter(verificationLink -> verificationLink.getExpiredDateTime().isAfter(LocalDateTime.now()))
                .map(filteredObiekt -> updateLink(filteredObiekt))
                .orElse(false);
    }

    private Boolean updateLink(VerificationLink verificationLink) {
        verificationLink.setConfirmedDateTime(LocalDateTime.now());
        verificationLink.setIsConsumed(Boolean.TRUE);
        verificationLink.getUser().setEnabled(true);
        VerificationLink response = verificationLinkRepository.save(verificationLink);
        return response instanceof VerificationLink ? true : false;
    }
}
