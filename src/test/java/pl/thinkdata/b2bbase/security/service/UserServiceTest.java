package pl.thinkdata.b2bbase.security.service;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void init(@Mock UserDetailsService userDetailsService,
              @Mock UserRepository userRepository,
              @Mock MessageGenerator messageGenerator,
              @Mock VerificationLinkService verificationLinkService,
              @Mock AuthenticationManager authenticationManager) {


        this.userService = new UserService(
                "secret",
                2592000000L,
                userDetailsService,
                userRepository,
                messageGenerator,
                verificationLinkService,
                authenticationManager);
    }

    @Test
    void shouldThrowInvalidRequestDataException() {
        assertThrows(InvalidRequestDataException.class, () -> userService.getUserData(null));
    }

    @Test
    @Disabled
    void shouldThrowInvalidRequestDataException2() {
        Exception exception = assertThrows(InvalidRequestDataException.class, () -> userService.getUserData(null));
        assertEquals("Token musi zawierać wartość", exception.getMessage());
    }
}