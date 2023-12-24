package pl.thinkdata.b2bbase.catalog.mapper;

import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogExtended;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogWithCategory;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;
import java.util.stream.Collectors;

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
                .active(company.isActive())
                .build();
    }

    public static CompanyInCatalogWithCategory mapToCompanyInCatalogWithCategory(Company company) {
        return CompanyInCatalogWithCategory.builder()
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
                .categories(company.getCategories().stream()
                        .map(category -> mapToCategoryToCatalog(category))
                        .collect(Collectors.toList()))
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
                .active(company.isActive())
                .build();
    }

    public static CategoryToCatalog mapToCategoryToCatalog(Category category) {
        return CategoryToCatalog.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }
}
