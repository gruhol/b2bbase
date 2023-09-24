package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.pl.NIP;
import pl.thinkdata.b2bbase.company.model.CompanyTypeEnum;
import pl.thinkdata.b2bbase.company.model.LegalFormEnum;
import pl.thinkdata.b2bbase.common.validator.PhoneValidation;

@Getter
@Setter
@Builder
public class CompanyDto extends AbstractCompany {
    @NotBlank
    private String name;
    @NotNull
    private CompanyTypeEnum type;
    @NotNull
    private LegalFormEnum legalFormEnum;
    @NotBlank
    @NIP
    private String nip;
    @NotBlank
    private String regon;
    private String krs;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @PhoneValidation
    private String phone;
    @URL
    private String wwwSite;
    @URL
    private String wwwStore;
}
