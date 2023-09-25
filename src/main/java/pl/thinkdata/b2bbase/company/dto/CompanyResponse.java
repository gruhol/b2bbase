package pl.thinkdata.b2bbase.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.CompanyTypeEnum;
import pl.thinkdata.b2bbase.company.model.LegalFormEnum;

import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {

    private String name;
    private String slug;
    private CompanyTypeEnum type;
    private LegalFormEnum legalForm;
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
    private String description;
    private String logo;
    private List<Branch> branches;
}
