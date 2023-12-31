package pl.thinkdata.b2bbase.search.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.company.model.enums.VoivodeshipEnum;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SearchCompanyResult {
    private Page<CompanyInCatalog> companies;
    private Set<CategoryToCatalog> categoryListForCompany;
    private List<VoivodeshipEnum> voivodeshipEnumList;
}
