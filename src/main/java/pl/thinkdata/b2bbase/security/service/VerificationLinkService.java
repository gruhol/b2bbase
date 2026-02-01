package pl.thinkdata.b2bbase.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.thinkdata.b2bbase.common.service.baseUrlService.BaseUrlService;
import pl.thinkdata.b2bbase.common.service.emailSenderService.EmailSenderService;
import pl.thinkdata.b2bbase.security.dto.VerificationLinkResponse;
import pl.thinkdata.b2bbase.security.model.PasswordToSendRequest;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLink;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.VerificationLinkRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VerificationLinkService {

    private final VerificationLinkRepository verificationLinkRepository;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final BaseUrlService baseUrlService;
    private String secret;
    private long expirationTime;

    private static final String URL = "url";
    private static final String BASEURL = "baseurl";

    public VerificationLinkService(VerificationLinkRepository verificationLinkRepository,
                                   EmailSenderService emailSenderService,
                                   TemplateEngine templateEngine,
                                   BaseUrlService baseUrlService,
                                   @Value("${jwt.secret}") String secret,
                                   @Value("${jwt.expirationTime}") long expirationTime) {
        this.verificationLinkRepository = verificationLinkRepository;
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
        this.baseUrlService = baseUrlService;
        this.secret = secret;
        this.expirationTime = expirationTime;
    }


    public void createVerificationLinkAndSendEmail(VerificationLinkRequest request) {
        findActiveTokenAndDeleteThem(request.getUser());

        VerificationLink link = new VerificationLink();
        link.setUser(request.getUser());
        verificationLinkRepository.save(link);

        Context context = new Context();
        String baseUrl = baseUrlService.getUrl();
        String url = baseUrl + request.getTargetUrl() + link.getToken();
        context.setVariable(URL, url);
        context.setVariable(BASEURL, baseUrl);
        String body = templateEngine.process(request.getEmailTemplate(), context);
        emailSenderService.sendEmail(request.getUser().getUsername(), request.getEmailSubject(), body, url);
    }

    public boolean checkVerificationLink(String token) {
        return verificationLinkRepository.findByToken(token)
                .filter(verificationLink -> !verificationLink.getIsConsumed())
                .filter(verificationLink -> verificationLink.getExpiredDateTime().isAfter(LocalDateTime.now()))
                .map(this::updateLink)
                .orElse(false);
    }

    public VerificationLinkResponse checkVerificationLinkAndSendToken(String token) {
        boolean checkedVerificationLink = checkVerificationLink(token);
        if (checkedVerificationLink) {
            VerificationLink verificationLink = verificationLinkRepository.findByToken(token).orElse(null);
            String loginToken =  JWT.create()
                    .withSubject(String.valueOf(verificationLink.getUser().getId()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .sign(Algorithm.HMAC256(secret));
            return VerificationLinkResponse.builder()
                    .token(loginToken)
                    .verified(true)
                    .build();
        }
        return VerificationLinkResponse.builder()
                .token(null)
                .verified(false)
                .build();
    }

    public boolean createEmailWithDataAndSendEmail(PasswordToSendRequest request) {

        String baseUrl = baseUrlService.getUrl();
        Context context = new Context();
        context.setVariable(BASEURL, baseUrl);
        for (Map.Entry variable : request.getListVariable().entrySet()) {
            context.setVariable(variable.getKey().toString(), variable.getValue());
        }
        String body = templateEngine.process(request.getEmailTemplate(), context);
        String newPassword = request.getListVariable().containsKey("newPassword") ? request.getListVariable().get("newPassword") : null;
        return emailSenderService.sendEmail(request.getUser().getUsername(), request.getEmailSubject(), body, newPassword);
    }

    private void findActiveTokenAndDeleteThem(User user) {
        List<Long> verificationLinkIdsList = verificationLinkRepository.findByUser(user).stream()
                .filter(link -> !link.getIsConsumed())
                .filter(link -> link.getExpiredDateTime().isAfter(LocalDateTime.now()))
                .map(VerificationLink::getId)
                .collect(Collectors.toList());

        verificationLinkRepository.deleteAllById(verificationLinkIdsList);
    }

    private Boolean updateLink(VerificationLink verificationLink) {
        verificationLink.setConfirmedDateTime(LocalDateTime.now());
        verificationLink.setIsConsumed(Boolean.TRUE);
        verificationLink.getUser().setEnabled(true);
        VerificationLink response = verificationLinkRepository.save(verificationLink);
        return Objects.equals(response.getId(), verificationLink.getId());
    }
}
