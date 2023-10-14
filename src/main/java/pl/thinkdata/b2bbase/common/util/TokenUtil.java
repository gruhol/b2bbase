package pl.thinkdata.b2bbase.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Optional;

import static java.util.Objects.isNull;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.*;

@Component
public class TokenUtil {

    private static final String TOKEN_PREFIX = "Bearer ";
    private String secret;
    private final MessageGenerator messageGenerator;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final UserRole2CompanyRepository userRole2CompanyRepository;

    public TokenUtil(@Value("${jwt.secret}") String secret,
                     MessageGenerator messageGenerator,
                     UserDetailsService userDetailsService, UserRepository userRepository, UserRole2CompanyRepository userRole2CompanyRepository)
    {
        this.secret = secret;
        this.messageGenerator = messageGenerator;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userRole2CompanyRepository = userRole2CompanyRepository;
    }

    public String validTokenAndGetUsername(String token) {
        if (isNull(token)) throw new InvalidRequestDataException(messageGenerator.get(TOKEN_CAN_NOT_BY_NULL));
        if (!token.startsWith(TOKEN_PREFIX)) throw new InvalidRequestDataException(messageGenerator.get(WRONG_TOKEN_PREFIX));
        String userName =  JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
        if (isNull(userName)) throw new InvalidRequestDataException(messageGenerator.get(TOKEN_HAVE_TO_CONTAINS_USERNAME));
        return userName;
    }

    public Company getCompanyByUsernameFormDataBase(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername())).get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));

        return Optional.ofNullable(userRole2CompanyRepository.findByUser(user))
                .get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(YOU_DONT_OWN_ANY_COMPANIES)))
                .getCompany();
    }
}
