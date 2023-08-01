package pl.thinkdata.b2bbase.common.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.thinkdata.b2bbase.common.model.MyExceptionResponse;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import java.time.ZonedDateTime;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.TOKEN_IS_NOT_VALID;

@RestControllerAdvice
public class HandleJWTDecodeException {

    @Autowired
    private MessageGenerator messageGenerator;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({JWTDecodeException.class})
    public ResponseEntity<MyExceptionResponse> handleAuthorizationException(
            JWTDecodeException ex,
            HttpServletRequest request)
    {
        MyExceptionResponse body = MyExceptionResponse.builder()
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURI())
                .message(messageGenerator.get(TOKEN_IS_NOT_VALID))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
