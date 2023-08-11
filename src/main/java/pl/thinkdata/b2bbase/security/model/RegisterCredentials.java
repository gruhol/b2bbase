package pl.thinkdata.b2bbase.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import pl.thinkdata.b2bbase.security.validator.PhoneValidation;

@Getter
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