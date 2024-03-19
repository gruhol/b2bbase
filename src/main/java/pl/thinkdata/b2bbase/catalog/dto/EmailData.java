package pl.thinkdata.b2bbase.catalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.thinkdata.b2bbase.common.validator.PhoneValidation;

@Getter
@Setter
public class EmailData {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @PhoneValidation
    private String phone;
    @NotBlank
    private String message;
    @NotNull
    private Long companyId;
}
