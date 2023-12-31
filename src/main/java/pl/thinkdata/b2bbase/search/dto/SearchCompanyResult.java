package pl.thinkdata.b2bbase.search.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SearchCompanyResult {
    private Page<CompanyInCatalog> companies;
    private Set<CategoryToCatalog> categoryListForCompany;
    private Map<String, String> voivodeshipEnumList;
}
