package pl.thinkdata.b2bbase.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.thinkdata.b2bbase.common.service.emailservice.MyEmailService;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLink;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.VerificationLinkRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationLinkService {

    private final VerificationLinkRepository verificationLinkRepository;
    private final MyEmailService myEmailService;
    private final TemplateEngine templateEngine;
    private final HttpServletRequest request;

    private static final String URL = "url";
    private static final String BASEURL = "baseurl";


    public void createVerificationLinkAndSendEmail(VerificationLinkRequest request) {
        VerificationLink link = new VerificationLink();
        link.setUser(request.getUser());
        verificationLinkRepository.save(link);
        Context context = new Context();
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(this.request)
                .replacePath(null)
                .build()
                .toUriString();
        String url = baseUrl + request.getTargetUrl() + link.getToken();
        context.setVariable(URL, url);
        context.setVariable(BASEURL, baseUrl);
        String body = templateEngine.process(request.getEmailTemplate(), context);
        myEmailService.sendEmail(request.getUser().getUsername(), request.getEmailSubject(), body, url);
    }

    private void findActiveTokenAndDelete(User user) {
        // todo
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
