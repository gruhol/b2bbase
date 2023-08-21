package pl.thinkdata.b2bbase.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.dto.RegisterCredentials;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Inject
    private UserService userService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private VerificationLinkService verificationLinkService;
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
    void init(@Mock AuthenticationManager authenticationManager) {

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
                this.verificationLinkService,
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

    @Test
    void shouldReturnEmptyList() {
        //when
        List<String> resultList = userService.getRole(tempTokenWithoutUsername);
        List<String> resultListFormNullToken = userService.getRole(null);
        //then
        assertEquals(0, resultList.size());
        assertEquals(0, resultListFormNullToken.size());
    }

    @Test
    void shouldReturnUserRole() {
        //when
        when(userDetailsService.loadUserByUsername(any())).thenReturn(createTempUserDetails());
        List<String> resultList = userService.getRole(tempTokenWithUsername);
        //then
        assertEquals(1, resultList.size());
        assertEquals("TEST_ROLE", resultList.get(0));
    }

    @Test
    void shouldReturnEmptyListWhenThrowUsernameNotFoundException() {
        //when
        when(userDetailsService.loadUserByUsername(any())).thenThrow(new UsernameNotFoundException("Test"));
        List<String> resultList = userService.getRole(tempTokenWithUsername);
        assertEquals(0, resultList.size());
    }

    @Test
    void shouldThrowValidationExceptionThenPasswordsIsNotTheSame() {
        //given
        RegisterCredentials registerCredentials = createTempRegisterCredentialsWithWrongPassword();
        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.register(registerCredentials));
        //then
        assertEquals(2, exception.getFileds().size());
        assertEquals("Hasła muszą być identyczne", exception.getFileds().get("password"));
        assertEquals("Hasła muszą być identyczne", exception.getFileds().get("repeatPassword"));
    }

    @Test
    void shouldThrowValidationExceptionThenUserIsInDataBase() {
        //given
        RegisterCredentials registerCredentials = createTempRegisterCredentials();
        //when
        when(userRepository.existsByUsername(any())).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class, () -> userService.register(registerCredentials));
        assertEquals(1, exception.getFileds().size());
        assertEquals("Użytkownik o takim email już istnieje w bazie", exception.getFileds().get("username"));
    }

    @Test
    void shouldReturnTrueWhenRegisterUser() {
        //given
        RegisterCredentials registerCredentials = createTempRegisterCredentials();
        //when
        boolean result = userService.register(registerCredentials);
        //then
        verify(userRepository, times(1)).save(any());
        verify(userRepository, times(1)).existsByUsername(any());
        verify(verificationLinkService, times(1)).createVerificationLinkAndSendEmail(any());
        verify(verificationLinkService, times(1)).createVerificationLinkAndSendEmail(any());
        assertEquals(true, result);
    }

    private static RegisterCredentials createTempRegisterCredentialsWithWrongPassword() {
        return RegisterCredentials.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .username("kowalski@test.pl")
                .password("password")
                .repeatPassword("password1")
                .phone("123456789")
                .build();
    }

    private static RegisterCredentials createTempRegisterCredentials() {
        return RegisterCredentials.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .username("kowalski@test.pl")
                .password("password")
                .repeatPassword("password")
                .phone("123456789")
                .build();
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
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("TEST_ROLE");
        authorityList.add(grantedAuthority);
        PrivateUserDetails privateUserDetails = new PrivateUserDetails("Jon", "Doe", authorityList);
        privateUserDetails.setId(2L);
        return privateUserDetails;
    }
}