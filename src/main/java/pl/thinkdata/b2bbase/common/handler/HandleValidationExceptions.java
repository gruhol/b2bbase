package pl.thinkdata.b2bbase.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.thinkdata.b2bbase.common.error.ValidationErrors;
import pl.thinkdata.b2bbase.common.model.ValidationExceptionResponse;

@RestControllerAdvice
public class HandleValidationExceptions {

    public static final String error_message = "Validation error. The following fields contain a validation error.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse();
        validationExceptionResponse.setMessage(error_message);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationExceptionResponse.getFields().put(fieldName, errorMessage);
        });
        return ResponseEntity.status(400).body(validationExceptionResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handleValidationExceptions2(ValidationErrors ex) {
        ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse();
        validationExceptionResponse.setMessage(ex.getMessage());
        validationExceptionResponse.setFields(ex.getFileds());
        return ResponseEntity.status(400).body(validationExceptionResponse);
    }
}
