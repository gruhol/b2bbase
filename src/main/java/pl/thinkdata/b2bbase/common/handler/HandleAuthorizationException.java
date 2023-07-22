package pl.thinkdata.b2bbase.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.thinkdata.b2bbase.common.error.AuthorizationException;
import pl.thinkdata.b2bbase.common.model.AuthorizationExceptionResponse;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class HandleAuthorizationException {

    @Autowired
    private MessageGenerator messageGenerator;

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<AuthorizationExceptionResponse> handleAuthorizationException(
            AuthorizationException ex,
            HttpServletRequest request)
    {
        AuthorizationExceptionResponse body = AuthorizationExceptionResponse.builder()
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .url(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
