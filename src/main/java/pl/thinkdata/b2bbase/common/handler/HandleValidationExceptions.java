package pl.thinkdata.b2bbase.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.model.ValidationExceptionResponse;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class HandleValidationExceptions {

    public static final String error_message = "Validation error. The following fields contain a validation error.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request)
    {
        ValidationExceptionResponse body = new ValidationExceptionResponse();
        body.setTimestamp(ZonedDateTime.now());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setMessage(error_message);
        body.setUrl(request.getRequestURI());

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            body.getFields().put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ValidationExceptionResponse> handleValidationExceptions(
            ValidationException ex,
            HttpServletRequest request)
    {
        ValidationExceptionResponse body = new ValidationExceptionResponse();
        body.setTimestamp(ZonedDateTime.now());
        body.setStatus(HttpStatus.BAD_REQUEST.value());
        body.setMessage(ex.getMessage());
        body.setFields(ex.getFileds());
        body.setUrl(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
