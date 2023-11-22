package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalog;

@Service
@RequiredArgsConstructor
public class CatalogCompanyService {

    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public Page<CompanyInCatalog> getCompanies2(List<Long> idCategories,
                                                Boolean isEdiCooperation,
                                                Boolean isApiCooperation,
                                                Boolean isProductFileCooperation,
                                                Pageable pageable) {
        Page<Company> companies = companyRepository.getAllCompanyToCatalog(idCategories, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }
}
