package pl.thinkdata.b2bbase.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.AuthorizationException;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.LoginDictionary;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.dto.UserEditData;
import pl.thinkdata.b2bbase.security.model.PrivateUserDetails;
import pl.thinkdata.b2bbase.security.dto.RegisterCredentials;
import pl.thinkdata.b2bbase.security.model.Token;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.UserRole;
import pl.thinkdata.b2bbase.security.model.VerificationLinkRequest;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.AUTHORIZATION_FAILED;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.TOKEN_CAN_NOT_BY_NULL;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.TOKEN_HAVE_TO_CONTAINS_USERNAME;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_FROM_GIVEN_TOKEN_NOT_FOUND;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_IS_NOT_ACTIVATED;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.WRONG_TOKEN_PREFIX;
import static pl.thinkdata.b2bbase.security.mapper.UserMapper.mapToUserEditData;

@Service
public class UserService {

    public static final String error_message = "Validation error. The following fields contain a validation error.";
    private static final String CONFIRM_YOUR_REGISTRATION = "confirm.your.registration";
    private static final String VERIFY = "/verify/";
    private static final String TOKEN_PREFIX = "Bearer ";
    private String secret;
    private long expirationTime;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final MessageGenerator messageGenerator;
    private final VerificationLinkService verificationLinkService;
    private final AuthenticationManager authenticationManager;

    public UserService(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expirationTime}") long expirationTime,
                       UserDetailsService userDetailsService,
                       UserRepository userRepository,
                       MessageGenerator messageGenerator,
                       VerificationLinkService verificationLinkService,
                       AuthenticationManager authenticationManager) {
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.messageGenerator = messageGenerator;
        this.verificationLinkService = verificationLinkService;
        this.authenticationManager = authenticationManager;
    }

    public List<String> getRole(String token) {
        if (isNull(token)) return Collections.emptyList();
        try {
            String userName = getUsernameFromToken(token);
            if (isNull(userName)) return Collections.emptyList();
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            return userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public UserEditData getUserData(String token) {
        if (isNull(token)) throw new InvalidRequestDataException(messageGenerator.get(TOKEN_CAN_NOT_BY_NULL));
        if (!token.startsWith(TOKEN_PREFIX)) throw new InvalidRequestDataException(messageGenerator.get(WRONG_TOKEN_PREFIX));

        String userName = getUsernameFromToken(token);
        if (isNull(userName)) throw new InvalidRequestDataException(messageGenerator.get(TOKEN_HAVE_TO_CONTAINS_USERNAME));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername()))
                .get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));
        return mapToUserEditData(user);
    }

    public boolean register(RegisterCredentials registerCredentials) {
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
                .firstname(registerCredentials.getFirstName())
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

    public Token authenticate(String username, String password) {
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

    private String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }
}
