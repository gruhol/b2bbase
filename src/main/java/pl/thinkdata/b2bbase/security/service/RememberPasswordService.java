package pl.thinkdata.b2bbase.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RememberPasswordService {

    public static final String CONFIRM_THE_PASSWORD_RESET_REQUEST = "confirm.the.password.reset";
    public static final String SEND_PASSWORD = "/send-password/";
    private final UserRepository userRepository;
    private final VerificationLinkService verificationLinkService;
    private final MessageGenerator messageGenerator;

    public boolean checkUserNameAndSendLinkWithTokenToRememberPassword(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()) return true;
        verificationLinkService.createVerificationLinkAndSendEmail(createVerificationLink(user.get()));
        return true;
    }

    private VerificationLinkRequest createVerificationLink(User user) {
        return VerificationLinkRequest
                .builder()
                .user(user)
                .emailSubject(messageGenerator.get(CONFIRM_THE_PASSWORD_RESET_REQUEST))
                .emailTemplate("email-templates/confirm_password_reset")
                .targetUrl(SEND_PASSWORD)
                .build();
    }
}
