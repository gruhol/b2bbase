package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;
import pl.thinkdata.b2bbase.company.model.ComapnyType;
import pl.thinkdata.b2bbase.company.model.LegalForm;

@Getter
public class CompanyDto {
    @NotBlank
    private String name;
    @NotBlank
    @UniqueElements
    private String slug;
    private ComapnyType type;
    private LegalForm legal_form;
    private String nip;
    private String regon;
    private String krs;
    private String email;
    private String phone;
    private String www_site;
    private String www_store;
}
