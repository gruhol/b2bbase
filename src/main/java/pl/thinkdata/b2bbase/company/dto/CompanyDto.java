package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.pl.NIP;
import pl.thinkdata.b2bbase.company.model.ComapnyType;
import pl.thinkdata.b2bbase.company.model.LegalForm;

@Getter
@Setter
public class CompanyDto {
    @NotBlank
    private String name;
    @NotBlank
    private String slug;
    @NotNull
    private ComapnyType type;
    @NotNull
    private LegalForm legalForm;
    @NotBlank
    @NIP
    private String nip;
    @NotBlank
    private String regon;
    private String krs;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @URL
    private String wwwSite;
    @URL
    private String wwwStore;
}
