package pl.thinkdata.b2bbase.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.dto.UserEditData;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.TOKEN_CAN_NOT_BY_NULL;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.TOKEN_HAVE_TO_CONTAINS_USERNAME;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_FROM_GIVEN_TOKEN_NOT_FOUND;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.WRONG_TOKEN_PREFIX;
import static pl.thinkdata.b2bbase.security.mapper.UserMapper.mapToUserEditData;

@Service
public class UserService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private String secret;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final MessageGenerator messageGenerator;

    public UserService(@Value("${jwt.secret}") String secret,
                       UserDetailsService userDetailsService, UserRepository userRepository, MessageGenerator messageGenerator) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.messageGenerator = messageGenerator;
    }

    public List<String> getRole(String token) {
        if (token == null) return Collections.emptyList();
        try {
            String userName = getUsernameFromToken(token);
            if (userName == null) return Collections.emptyList();
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            return userDetails.getAuthorities().stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public UserEditData getUserData(String token) {
        if (token == null) throw new InvalidRequestDataException(messageGenerator.get(TOKEN_CAN_NOT_BY_NULL));
        String userName;
        if (!token.startsWith(TOKEN_PREFIX)) throw new InvalidRequestDataException(messageGenerator.get(WRONG_TOKEN_PREFIX));
        userName = getUsernameFromToken(token);

        if (userName == null) throw new InvalidRequestDataException(messageGenerator.get(TOKEN_HAVE_TO_CONTAINS_USERNAME));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername()))
                .get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));
        return mapToUserEditData(user);
    }

    private String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
    }
}
