package pl.thinkdata.b2bbase.catalog.dto;

import lombok.*;
import pl.thinkdata.b2bbase.company.model.CompanyTypeEnum;
import pl.thinkdata.b2bbase.company.model.LegalFormEnum;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CompanyInCatalog {
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
    private String logo;
}
