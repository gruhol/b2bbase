package pl.thinkdata.b2bbase.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import static java.util.Objects.isNull;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.*;

@Component
public class TokenUtil {

    private static final String TOKEN_PREFIX = "Bearer ";
    private String secret;
    private final MessageGenerator messageGenerator;

    public TokenUtil(@Value("${jwt.secret}") String secret, MessageGenerator messageGenerator) {
        this.secret = secret;
        this.messageGenerator = messageGenerator;
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
}
