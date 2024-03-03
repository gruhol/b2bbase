package pl.thinkdata.b2bbase.security.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEditData {
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private boolean emailAgreement;
    private boolean smsAgreement;
}
