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

    @GetMapping({"/{idCategories}", "/{idCategories}/{isEdiCooperation}"})
    @ResponseBody
    public Page<CompanyInCatalog> getCompanies(@PathVariable List<Long> idCategories, @PathVariable(required = false) boolean isEdiCooperation, Pageable pageable) {
        return catalogCompanyService.getCompanies(idCategories, isEdiCooperation, pageable);
    }

    private String wynik;

    @GetMapping("/category")
    public List<CategoryToCatalog> getCategory() {
        return catalogCategoryService.getCategory();
    }

    @GetMapping("/get/{idCompanies}/{idBranches}")
    public Page<CompanyInCatalog2> getCompanies(@PathVariable List<String> idCompanies, @PathVariable List<String> idBranches, Pageable pageable) {
        String dupa = "dupa";
        return catalogCompanyService.getCompanies2(idCompanies, idBranches, pageable);
    }
}
