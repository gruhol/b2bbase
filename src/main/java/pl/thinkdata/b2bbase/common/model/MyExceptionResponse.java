package pl.thinkdata.b2bbase.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class MyExceptionResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private String url;
}
