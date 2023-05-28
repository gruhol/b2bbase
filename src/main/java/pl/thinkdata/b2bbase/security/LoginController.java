package pl.thinkdata.b2bbase.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.security.model.PrivateUserDetails;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.UserRole;
import pl.thinkdata.b2bbase.security.repository.UserRepository;
import pl.thinkdata.b2bbase.user.validator.PhoneValidation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private long expirationTime;
    private String secret;

    public LoginController(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           @Value("${jwt.expirationTime}") long expirationTime,
                           @Value("${jwt.secret}") String secret) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.expirationTime = expirationTime;
        this.secret = secret;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials) {
        return authenticate(loginCredentials.username, loginCredentials.password);
    }

    @PostMapping("/register")
    public Token register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        if(!registerCredentials.getPassword().equals(registerCredentials.getRepeatPassword())) {
            throw new IllegalArgumentException("Hasła nie się identyczne");
        }
        if (userRepository.existsByUsername(registerCredentials.getUsername())) {
            throw new IllegalArgumentException("Taki użytkownik istnieje w bazie danych");
        };
        userRepository.save(User.builder()
                .username(registerCredentials.getUsername())
                .phone(registerCredentials.getPhone())
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode(registerCredentials.getPassword()))
                .enabled(true)
                .authorities(List.of(UserRole.ROLE_USER))
                .build());

        return authenticate(registerCredentials.getUsername(), registerCredentials.getPassword());
    }

    private Token authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getId(), password)
        );

        PrivateUserDetails principal = (PrivateUserDetails) authenticate.getPrincipal();

        String token =  JWT.create()
                .withSubject(String.valueOf(principal.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(secret));
        List<String> roles = principal.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
        return new Token(token, roles);

    }

    @Getter
    private static class  LoginCredentials {
        private String username;
        private String password;
    }

    @Getter
    private static class  RegisterCredentials {
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
        private List<String> role;

        public Token(String token, List<String> role) {
            this.token = token;
            this.role = role;
        }

    }
}
