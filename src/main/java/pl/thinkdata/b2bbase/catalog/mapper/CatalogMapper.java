package pl.thinkdata.b2bbase.catalog.mapper;

import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogExtended;
import pl.thinkdata.b2bbase.company.model.Company;

public class CatalogMapper {

    public static CompanyInCatalog mapToCompanyInCatalog(Company company) {
        return CompanyInCatalog.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .type(company.getType())
                .legalForm(company.getLegalForm())
                .nip(company.getNip())
                .regon(company.getRegon())
                .krs(company.getKrs())
                .email(company.getEmail())
                .phone(company.getPhone())
                .wwwSite(company.getWwwSite())
                .wwwStore(company.getWwwStore())
                .ediCooperation(company.isEdiCooperation())
                .apiCooperation(company.isApiCooperation())
                .logo(company.getLogo())
                .build();
    }

    public static CompanyInCatalogExtended mapToCompanyInCatalogExtended(Company company) {
        return CompanyInCatalogExtended.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .type(company.getType())
                .legalForm(company.getLegalForm())
                .nip(company.getNip())
                .regon(company.getRegon())
                .krs(company.getKrs())
                .email(company.getEmail())
                .phone(company.getPhone())
                .wwwSite(company.getWwwSite())
                .wwwStore(company.getWwwStore())
                .description(company.getDescription())
                .ediCooperation(company.isEdiCooperation())
                .apiCooperation(company.isApiCooperation())
                .logo(company.getLogo())
                .build();
    }
}
