package pl.thinkdata.b2bbase.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private String message;
    private Map<String, String> fileds;

    public ValidationException(String error_message, Map<String, String> fields) {
        super(error_message);
        this.message = error_message;
        this.fileds = fields;
    }
}
