package pl.thinkdata.b2bbase.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.model.UserRole2Company;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.security.model.PrivateUserDetails;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private TokenUtil tokenUtil;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private UserRole2CompanyRepository userRole2CompanyRepository;
    @Mock
    private CategoryRepository categoryRepository;
    private CompanyService companyService;

    @BeforeEach
    void init() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(-1);
        messageSource.setBasenames("classpath:i18n/messages");

        this.companyService = new CompanyService(
                this.companyRepository,
                this.tokenUtil,
                this.userRepository,
                new MessageGenerator(messageSource),
                this.userDetailsService,
                this.userRole2CompanyRepository,
                this.categoryRepository);
    }

    @Test
    void shouldThrowInvalidRequestDataExceptionWhenUserAlreadyHasCompany() {
        //given
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("role");
        UserDetails userDetails = new PrivateUserDetails("name", "password", Arrays.asList(grantedAuthority));
        User user = User.builder()
                .username("test@test.pl")
                .build();
        UserRole2Company userRole2Company = new UserRole2Company();
        userRole2Company.setId(1L);
        //when
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
        when(userRepository.findByUsername(userDetails.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(userDetails.getUsername())).thenReturn(Optional.of(user));
        when(userRole2CompanyRepository.findByUser(user)).thenReturn(Optional.of(userRole2Company));
        //then
        InvalidRequestDataException exception = assertThrows(InvalidRequestDataException.class,
                () -> companyService.addCompany(new CompanyDto(), new MockHttpServletRequest()));
        assertEquals("Możesz dodać tylko jedną firmę", exception.getMessage());
    }

}