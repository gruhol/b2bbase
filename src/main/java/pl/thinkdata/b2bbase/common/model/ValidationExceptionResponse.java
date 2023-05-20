package pl.thinkdata.b2bbase.common.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ValidationExceptionResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> fields = new HashMap<>();
    private String url;

    public void setFields (Map<String, String> fields) {
        this.fields.putAll(fields);
    }
}
