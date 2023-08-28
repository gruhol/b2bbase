package pl.thinkdata.b2bbase.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import pl.thinkdata.b2bbase.common.validator.PhoneValidation;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCredentials {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    @PhoneValidation
    private String phone;
}