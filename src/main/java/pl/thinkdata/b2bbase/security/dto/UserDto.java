package pl.thinkdata.b2bbase.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import pl.thinkdata.b2bbase.security.validator.PhoneValidation;

public class UserDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String username;
    private String password;
    private String newPassword;
    private String repeatNewPassword;
    @PhoneValidation
    private String phone;
}
