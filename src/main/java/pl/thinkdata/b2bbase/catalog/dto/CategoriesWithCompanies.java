package pl.thinkdata.b2bbase.catalog.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoriesWithCompanies {
    private CategoryExtended category;
    private Page<CompanyInCatalog> listCompany;
}
