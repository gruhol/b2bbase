package pl.thinkdata.b2bbase.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.dto.UserEditData;
import pl.thinkdata.b2bbase.security.model.PrivateUserDetails;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ImportAutoConfiguration(ReloadableResourceBundleMessageSource.class)
class UserServiceTest {

    @Inject
    private UserService userService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private UserRepository userRepository;
    private static final long expirationTime = 2592000000L;
    private static final String SECRET = "secret";
    private static final String TOKEN_PREFIX = "Bearer ";

    String tempTokenWithoutUsername = TOKEN_PREFIX + JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
            .sign(Algorithm.HMAC256(SECRET));

    String tempTokenWithUsername = TOKEN_PREFIX + JWT.create()
            .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
            .withSubject(String.valueOf(2))
            .sign(Algorithm.HMAC256(SECRET));

    @BeforeEach
    void init(@Mock VerificationLinkService verificationLinkService,
              @Mock AuthenticationManager authenticationManager) {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");

        this.userService = new UserService(
                SECRET,
                expirationTime,
                this.userDetailsService,
                this.userRepository,
                new MessageGenerator(messageSource),
                verificationLinkService,
                authenticationManager);
    }

    @Test
    void shouldThrowInvalidRequestDataException_WhenGetUserDataWithNullToken() {
        Exception exception = assertThrows(InvalidRequestDataException.class, () -> userService.getUserData(null));
        assertEquals("Token musi zawierać wartość", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRequestDataException_WhenGetUserDataWithoutTokenPrefix() {
        Exception exception = assertThrows(InvalidRequestDataException.class, () -> userService.getUserData("1234567890"));
        assertEquals("Niepoprawny token prefix.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRequestDataException_WhenGetUserDataWithoutUsernameInToken() {
        Exception exception = assertThrows(InvalidRequestDataException.class, () -> userService.getUserData(tempTokenWithoutUsername));
        assertEquals("Token musi zawierać nazwę użytkownika", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidRequestDataException_WhenUserNotFoundInDataBase() {

        when(userDetailsService.loadUserByUsername(any())).thenReturn(createTempUserDetails());
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(InvalidRequestDataException.class, () -> userService.getUserData(tempTokenWithUsername));
        assertEquals("Nie znaleziono użytkownika z podanego tokena.", exception.getMessage());
    }

    @Test
    void shouldReturnUserData_WhenUserIsInDataBase() {
        //given
        User user = createTempUser();
        //when
        when(userDetailsService.loadUserByUsername(any())).thenReturn(createTempUserDetails());
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        UserEditData response = userService.getUserData(tempTokenWithUsername);
        //then
        assertEquals("Jan", response.getFirstName());
        assertEquals("Kowalski", response.getLastName());
        assertEquals("666777888", response.getPhone());
        assertEquals("jan.kowalski@test.com", response.getUsername());
    }

    private User createTempUser() {
        return User.builder()
                .firstname("Jan")
                .lastname("Kowalski")
                .phone("666777888")
                .username("jan.kowalski@test.com")
                .password("21eh1ej19e19eh1")
                .build();
    }

    private PrivateUserDetails createTempUserDetails() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        PrivateUserDetails privateUserDetails = new PrivateUserDetails("Jon", "Doe", authorityList);
        privateUserDetails.setId(2L);
        return privateUserDetails;
    }
}