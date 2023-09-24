package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.thinkdata.b2bbase.common.validator.PhoneValidation;
import pl.thinkdata.b2bbase.company.model.VoivodeshipEnum;

@Getter
@Setter
public class BranchDto {
    @NotBlank
    @Size(min = 2, max = 256)
    private String name;
    private boolean headquarter;
    private String slug;
    @NotNull
    private VoivodeshipEnum voivodeship;
    @NotBlank
    @Size(min = 6, max = 6)
    private String post_code;
    @NotBlank
    @Size(min = 2, max = 32)
    private String city;
    @NotBlank
    @Size(min = 2, max = 128)
    private String street;
    @NotBlank
    @Size(min = 1, max = 16)
    private String house_number;
    @Size(min = 1, max = 16)
    private String office_number;
    @Email
    @Size(max = 256)
    private String email;
    @PhoneValidation
    @Size(max = 11)
    private String phone;
    private String latitude;
    private String longitude;
    private Long companyId;
}
