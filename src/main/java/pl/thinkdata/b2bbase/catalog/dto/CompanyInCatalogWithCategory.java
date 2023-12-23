package pl.thinkdata.b2bbase.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.enums.CompanyTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.LegalFormEnum;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CompanyInCatalogWithCategory {
    private Long id;
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
    private boolean active;
    private String logo;
    private Branch branch;
    private List<CategoryToCatalog> categories;
}
