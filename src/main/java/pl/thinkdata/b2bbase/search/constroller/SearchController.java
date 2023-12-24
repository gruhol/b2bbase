package pl.thinkdata.b2bbase.search.constroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.service.CatalogCompanyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final CatalogCompanyService catalogCompanyService;

    @GetMapping("/{keyword}")
    public Page<CompanyInCatalog> searchCompany(@PathVariable(required = true) String keyword, Pageable pageable) {
        return catalogCompanyService.searchCompanyByKeyword("%" + keyword + "%", pageable);
    }
}
