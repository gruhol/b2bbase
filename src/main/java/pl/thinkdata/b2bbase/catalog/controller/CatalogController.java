package pl.thinkdata.b2bbase.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogExtended;
import pl.thinkdata.b2bbase.catalog.service.CatalogCategoryService;
import pl.thinkdata.b2bbase.catalog.service.CatalogCompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogCompanyService catalogCompanyService;
    private final CatalogCategoryService catalogCategoryService;

    @GetMapping("/category")
    public List<CategoryToCatalog> getCategory() {
        return catalogCategoryService.getCategory();
    }

    @GetMapping("/company/{slug}")
    public CompanyInCatalogExtended getCompanyInCatalogBySlug(@PathVariable String slug) {
        return catalogCompanyService.getCompanyBySlug(slug);
    }

    @GetMapping({"/{slug}", "/"})
    public Page<CompanyInCatalog> getCompaniesBySlug(@PathVariable(required = false) String slug,
                                                @RequestParam(required = false) Boolean isEdiCooperation,
                                                @RequestParam(required = false) Boolean isApiCooperation,
                                                @RequestParam(required = false) Boolean isProductFileCooperation,
                                                Pageable pageable) {
        return catalogCompanyService.getCompaniesBySlug(slug, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
    }

    @GetMapping("/wholesales")
    public Page<CompanyInCatalog> getCompanies(@RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) List<String> voivodeshipSlugs,
                                               @RequestParam(required = false) Boolean isEdiCooperation,
                                               @RequestParam(required = false) Boolean isApiCooperation,
                                               @RequestParam(required = false) Boolean isProductFileCooperation,
                                               Pageable pageable) {
        return catalogCompanyService.getCompanies(categories, voivodeshipSlugs, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
    }
}
