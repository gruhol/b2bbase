package pl.thinkdata.b2bbase.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.model.MyExceptionResponse;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class HandleInvalidRequestDataException {

    @Autowired
    private MessageGenerator messageGenerator;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRequestDataException.class})
    public ResponseEntity<MyExceptionResponse> handleAuthorizationException(
            InvalidRequestDataException ex,
            HttpServletRequest request)
    {
        MyExceptionResponse body = MyExceptionResponse.builder()
                .timestamp(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .url(request.getRequestURI())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
