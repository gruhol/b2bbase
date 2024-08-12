package pl.thinkdata.b2bbase.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationLinkResponse {
    private String token;
    private boolean verified;
}
