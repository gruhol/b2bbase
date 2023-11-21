package pl.thinkdata.b2bbase.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog2;
import pl.thinkdata.b2bbase.catalog.service.CatalogCategoryService;
import pl.thinkdata.b2bbase.catalog.service.CatalogCompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogCompanyService catalogCompanyService;
    private final CatalogCategoryService catalogCategoryService;

    @GetMapping("/{idCategories}")
    @ResponseBody
    public Page<CompanyInCatalog> getCompanies(@PathVariable List<Long> idCategories,
                                               @RequestParam(required = false) Boolean isEdiCooperation,
                                               @RequestParam(required = false) Boolean isApiCooperation,
                                               @RequestParam(required = false) Boolean isProductFileCooperation,
                                               Pageable pageable) {
        return catalogCompanyService.getCompanies(idCategories, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
    }

    private String wynik;

    @GetMapping("/category")
    public List<CategoryToCatalog> getCategory() {
        return catalogCategoryService.getCategory();
    }

    @GetMapping("/get/{idCompanies}")
    public Page<CompanyInCatalog> getCompanies2(@PathVariable List<String> idCompanies, Pageable pageable) {
        return catalogCompanyService.getCompanies2(idCompanies, pageable);
    }
}
