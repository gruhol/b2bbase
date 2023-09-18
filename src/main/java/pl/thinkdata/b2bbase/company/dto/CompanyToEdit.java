package pl.thinkdata.b2bbase.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.thinkdata.b2bbase.company.model.CompanyType;
import pl.thinkdata.b2bbase.company.model.LegalForm;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyToEdit {
    private String name;
    private String slug;
    private CompanyType type;
    private LegalForm legalForm;
    private String nip;
    private String regon;
    private String krs;
    private String email;
    private String phone;
    private String wwwSite;
    private String wwwStore;
    private boolean ediCooperation;
    private boolean apiCooperation;
    private boolean productFileCooperation;
}
