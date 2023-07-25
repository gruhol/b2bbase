package pl.thinkdata.b2bbase.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationLinkRequest {
    private User user;
    private String emailSubject;
    private String emailTemplate;
    private String targetUrl;
}
