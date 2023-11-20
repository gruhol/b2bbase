package pl.thinkdata.b2bbase.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public Page<CompanyInCatalog> getCompanies(Pageable pageable) {
        return catalogCompanyService.getCompanies(pageable);
    }

    @GetMapping("/category")
    public List<CategoryToCatalog> getCategory() {
        return catalogCategoryService.getCategory();
    }

    @GetMapping("/get/{idCompanies}/{idBranches}")
    public Page<CompanyInCatalog2> getCompanies(@PathVariable Long[] idCompanies, @PathVariable Long[] idBranches, Pageable pageable) {
        return catalogCompanyService.getCompanies2(idCompanies, idBranches, pageable);
    }
}
