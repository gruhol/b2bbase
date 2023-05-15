package pl.thinkdata.b2bbase.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ValidationExceptionResponse {
    private String message;
    private Map<String, String> fields = new HashMap<>();
}
