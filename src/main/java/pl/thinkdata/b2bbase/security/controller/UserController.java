package pl.thinkdata.b2bbase.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.common.error.AuthorizationException;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.LoginDictionary;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.dto.UserEditData;
import pl.thinkdata.b2bbase.security.model.PrivateUserDetails;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.UserRole;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.UserRepository;
import pl.thinkdata.b2bbase.security.service.UserService;
import pl.thinkdata.b2bbase.security.service.VerificationLinkService;
import pl.thinkdata.b2bbase.security.validator.PhoneValidation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.AUTHORIZATION_FAILED;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_IS_NOT_ACTIVATED;

@RestController
public class UserController {
    private static final String TOKEN_HEADER = "Authorization";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final MessageGenerator messageGenerator;
    private final VerificationLinkService verificationLinkService;
    private final UserService userService;

    private long expirationTime;
    private String secret;
    public static final String error_message = "Validation error. The following fields contain a validation error.";
    private static final String CONFIRM_YOUR_REGISTRATION = "confirm.your.registration";
    private static final String VERIFY = "/verify/";

    public UserController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          UserDetailsService userDetailsService, @Value("${jwt.expirationTime}") long expirationTime,
                          @Value("${jwt.secret}") String secret,
                          MessageGenerator messageGenerator,
                          VerificationLinkService verificationLinkService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.userDetailsService = userDetailsService;
        this.messageGenerator = messageGenerator;
        this.verificationLinkService = verificationLinkService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials) {
        return authenticate(loginCredentials.username, loginCredentials.password);
    }

    @GetMapping("/user/role")
    public List<String> getRole(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        return userService.getRole(token);
    }

    @GetMapping("/user/get")
    public UserEditData getUserData(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        return userService.getUserData(token);
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        Map<String, String> fields = new HashMap<>();
        if(!registerCredentials.getPassword().equals(registerCredentials.getRepeatPassword())) {
            fields.put(LoginDictionary.PASSWORD, LoginDictionary.PASSWORD_AND_REPEAT_PASSWORD_MUST_BE_THE_SAME);
            fields.put(LoginDictionary.REPEAT_PASSWORD, LoginDictionary.PASSWORD_AND_REPEAT_PASSWORD_MUST_BE_THE_SAME);
            throw new ValidationException(error_message, fields);
        }
        if (userRepository.existsByUsername(registerCredentials.getUsername())) {
            fields.put(LoginDictionary.USERNAME, LoginDictionary.USERNAME_ALREADY_EXIST);
            throw new ValidationException(error_message, fields);
        };
        User user = userRepository.save(User.builder()
                .firstname(registerCredentials.firstName)
                .lastname(registerCredentials.getLastName())
                .username(registerCredentials.getUsername())
                .phone(registerCredentials.getPhone())
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode(registerCredentials.getPassword()))
                .enabled(false)
                .authorities(List.of(UserRole.ROLE_USER))
                .build());

        verificationLinkService.createVerificationLinkAndSendEmail(VerificationLinkRequest.builder()
                .user(user)
                .emailSubject(messageGenerator.get(CONFIRM_YOUR_REGISTRATION))
                .emailTemplate("email-templates/registration-confirmation")
                .targetUrl(VERIFY)
                .build());
        return true;
    }

    private Token authenticate(String username, String password) {
        PrivateUserDetails principal = null;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthorizationException(messageGenerator.get(AUTHORIZATION_FAILED)));
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getId(), password));
            principal = (PrivateUserDetails) authenticate.getPrincipal();
        } catch (Exception exception) {
            if (exception instanceof AuthenticationException) {
                throw new AuthorizationException(messageGenerator.get(AUTHORIZATION_FAILED));
            }
        }
        if (!user.isEnabled()) throw new AuthorizationException(messageGenerator.get(USER_IS_NOT_ACTIVATED));

        String token =  JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
        return new Token(token);
    }


    @Getter
    private static class  LoginCredentials {
        private String username;
        private String password;
    }

    @Getter
    private static class  RegisterCredentials {
        @NotBlank
        private String firstName;
        @NotBlank
        private String lastName;
        @NotBlank
        @Email
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String repeatPassword;
        @PhoneValidation
        private String phone;
    }

    @Getter
    private static class Token {
        private String token;

        public Token(String token) {
            this.token = token;
        }
    }
}