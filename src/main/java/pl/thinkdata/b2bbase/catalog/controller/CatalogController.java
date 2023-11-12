package pl.thinkdata.b2bbase.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.service.CatalogCompanyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogCompanyService catalogCompanyService;

    @GetMapping
    public Page<CompanyInCatalog> getCompanies(Pageable pageable) {
        return catalogCompanyService.getCompanies(pageable);
    }

}
