package pl.thinkdata.b2bbase.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import pl.thinkdata.b2bbase.security.service.VerificationLinkService;
import pl.thinkdata.b2bbase.user.validator.PhoneValidation;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.AUTHORIZATION_FAILED;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_IS_NOT_ACTIVATED;

@RestController
public class LoginController {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final MessageGenerator messageGenerator;
    private final VerificationLinkService verificationLinkService;

    private long expirationTime;
    private String secret;
    public static final String error_message = "Validation error. The following fields contain a validation error.";
    private static final String CONFIRM_YOUR_REGISTRATION = "confirm.your.registration";
    private static final String VERIFY = "/verify/";

    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           UserDetailsService userDetailsService, @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret,
                           MessageGenerator messageGenerator,
                           VerificationLinkService verificationLinkService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.userDetailsService = userDetailsService;
        this.messageGenerator = messageGenerator;
        this.verificationLinkService = verificationLinkService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials) {
        return authenticate(loginCredentials.username, loginCredentials.password);
    }

    @GetMapping("/getRole/{token}")
    public List<String> getRole(@PathVariable("token") String token) {
        if (token == null) return Collections.emptyList();
        try {
            String userName = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (userName == null) return Collections.emptyList();

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            return userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @PostMapping("/getUserData")
    public UserEditData getUserData(@RequestBody String token) {
        if (token != null) {
            String userName = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (userName != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername()))
                        .get().orElseThrow(() -> new AuthorizationException("ddsf"));
                return new UserEditData().builder()
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .username(user.getUsername())
                        .phone(user.getPhone())
                        .build();
            }
        }
        return null;
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

        verificationLinkService.createVerificationLinkAndSendEmail(VerificationLinkRequest
                .builder()
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