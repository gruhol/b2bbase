package pl.thinkdata.b2bbase.catalog.mapper;

import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogExtended;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogWithCategory;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
                .productFileCooperation(company.isProductFileCooperation())
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
                .productFileCooperation(company.isProductFileCooperation())
                .logo(company.getLogo())
                .categories(company.getCategories().stream()
                        .map(CatalogMapper::mapToCategoryToCatalog)
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
                .productFileCooperation(company.isProductFileCooperation())
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

    public static CategoryToCatalog mapToCategoryToCatalog(Category cat, List<Category> categories) {
        if (cat == null) return null;
        return CategoryToCatalog.builder()
                .id(cat.getId())
                .name(cat.getName())
                .slug(cat.getSlug())
                .children(createChildList(categories, cat))
                .build();
    }

    private static List<CategoryToCatalog> createChildList(List<Category> allCategory, Category parent) {
        if (allCategory == null && parent.getId() == null) return new ArrayList<>();
        return allCategory.stream()
                .filter(cat -> cat.getParent() != null)
                .filter(cat -> Objects.equals(cat.getParent().getId(), parent.getId()))
                .map(cat -> mapToCategoryToCatalog(cat, allCategory))
                .collect(Collectors.toList());
    }
}
