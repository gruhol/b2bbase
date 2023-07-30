package pl.thinkdata.b2bbase.security.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.thinkdata.b2bbase.common.service.emailservice.MyEmailService;
import pl.thinkdata.b2bbase.security.model.PasswordToSendRequest;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLink;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.VerificationLinkRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VerificationLinkService {

    private final VerificationLinkRepository verificationLinkRepository;
    @Autowired
    private MyEmailService myEmailService;
    private final TemplateEngine templateEngine;
    private final HttpServletRequest request;

    private static final String URL = "url";
    private static final String BASEURL = "baseurl";


    public void createVerificationLinkAndSendEmail(VerificationLinkRequest request) {
        findActiveTokenAndDeleteThem(request.getUser());

        VerificationLink link = new VerificationLink();
        link.setUser(request.getUser());
        verificationLinkRepository.save(link);

        Context context = new Context();
        String baseUrl = createBaseUrl();
        String url = baseUrl + request.getTargetUrl() + link.getToken();
        context.setVariable(URL, url);
        context.setVariable(BASEURL, baseUrl);
        String body = templateEngine.process(request.getEmailTemplate(), context);
        myEmailService.sendEmail(request.getUser().getUsername(), request.getEmailSubject(), body, url);
    }

    public boolean checkVerificationLink(String token) {
        return verificationLinkRepository.findByToken(token)
                .filter(verificationLink -> !verificationLink.getIsConsumed())
                .filter(verificationLink -> verificationLink.getExpiredDateTime().isAfter(LocalDateTime.now()))
                .map(filteredObiekt -> updateLink(filteredObiekt))
                .orElse(false);
    }

    public boolean createEmailWithDataAndSendEmail(PasswordToSendRequest request) {

        String baseUrl = createBaseUrl();
        Context context = new Context();
        context.setVariable(BASEURL, baseUrl);
        for (Map.Entry variable : request.getListVariable().entrySet()) {
            context.setVariable(variable.getKey().toString(), variable.getValue());
        }
        String body = templateEngine.process(request.getEmailTemplate(), context);
        String newPassword = request.getListVariable().containsKey("newPassword") ? request.getListVariable().get("newPassword") : null;
        return myEmailService.sendEmail(request.getUser().getUsername(), request.getEmailSubject(), body, newPassword);
    }

    private void findActiveTokenAndDeleteThem(User user) {
        List<Long> verificationLinkIdsList = verificationLinkRepository.findByUser(user).stream()
                .filter(link -> link.getIsConsumed() == false)
                .filter(link -> link.getExpiredDateTime().isAfter(LocalDateTime.now()))
                .map(link -> link.getId())
                .collect(Collectors.toList());

        verificationLinkRepository.deleteAllById(verificationLinkIdsList);
    }

    private String createBaseUrl() {
        return ServletUriComponentsBuilder.fromRequestUri(this.request)
                .replacePath(null)
                .build()
                .toUriString();
    }

    private Boolean updateLink(VerificationLink verificationLink) {
        verificationLink.setConfirmedDateTime(LocalDateTime.now());
        verificationLink.setIsConsumed(Boolean.TRUE);
        verificationLink.getUser().setEnabled(true);
        VerificationLink response = verificationLinkRepository.save(verificationLink);
        return response instanceof VerificationLink ? true : false;
    }
}
