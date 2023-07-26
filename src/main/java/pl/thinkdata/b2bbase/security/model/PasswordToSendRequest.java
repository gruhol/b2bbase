package pl.thinkdata.b2bbase.security.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PasswordToSendRequest {
    private User user;
    private String emailSubject;
    private String emailTemplate;
    private Map<String, String> listVariable;
}
