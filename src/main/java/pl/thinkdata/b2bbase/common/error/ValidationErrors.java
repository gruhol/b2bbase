package pl.thinkdata.b2bbase.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ValidationErrors extends RuntimeException {
    private String message;
    private Map<String, String> fileds = new HashMap<>();

    public ValidationErrors(String error_message, Map<String, String> fileds) {
        message = error_message;
        fileds.putAll(fileds);
    }
}
