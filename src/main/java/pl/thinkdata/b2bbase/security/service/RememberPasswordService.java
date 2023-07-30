package pl.thinkdata.b2bbase.security.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.component.PasswordGenerator;
import pl.thinkdata.b2bbase.security.model.PasswordToSendRequest;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.UserRepository;
import pl.thinkdata.b2bbase.security.repository.VerificationLinkRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RememberPasswordService {

    public static final String CONFIRM_THE_PASSWORD_RESET_REQUEST = "confirm.the.password.reset";
    public static final String SEND_PASSWORD = "/send-password/";
    private static final String NEW_PASSWORD = "newPassword";
    public static final String YOUR_NEW_PASSWORD = "your.new.password";

    private final UserRepository userRepository;
    private final VerificationLinkService verificationLinkService;
    private final MessageGenerator messageGenerator;
    private final PasswordGenerator passwordGenerator;
    private final VerificationLinkRepository verificationLinkRepository;


    private final Integer lengthRandomPassword;

    public RememberPasswordService(UserRepository userRepository,
                                   VerificationLinkService verificationLinkService,
                                   MessageGenerator messageGenerator,
                                   PasswordGenerator passwordGenerator,
                                   VerificationLinkRepository verificationLinkRepository,
                                   @Value("${lengthRandomPassword}") Integer lengthRandomPassword) {
        this.userRepository = userRepository;
        this.verificationLinkService = verificationLinkService;
        this.messageGenerator = messageGenerator;
        this.passwordGenerator = passwordGenerator;
        this.verificationLinkRepository = verificationLinkRepository;
        this.lengthRandomPassword = lengthRandomPassword;
    }

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

    public boolean checkTokenAndSendNewPassword(String token) {
        if (!verificationLinkService.checkVerificationLink(token)) return false;

        User user = verificationLinkRepository.findByToken(token).map(link -> link.getUser()).orElseThrow();
        //todo should throw if verification token is expired or taken.
        String newPassword = passwordGenerator.getRandomPassword(lengthRandomPassword);
        Map<String, String> listVariable = new HashMap();
        listVariable.put(NEW_PASSWORD, newPassword);

        user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        PasswordToSendRequest request = PasswordToSendRequest.builder()
                .user(user)
                .emailSubject(messageGenerator.get(YOUR_NEW_PASSWORD))
                .emailTemplate("email-templates/send-new-password")
                .listVariable(listVariable)
                .build();
        return verificationLinkService.createEmailWithDataAndSendEmail(request);
    }
}
