package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog2;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalog;

@Service
@RequiredArgsConstructor
public class CatalogCompanyService {

    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public Page<CompanyInCatalog> getCompanies(List<Long> idCategories,
                                               Boolean isEdiCooperation,
                                               Boolean isApiCooperation,
                                               Boolean isProductFileCooperation,
                                               Pageable pageable) {

        List<Category> cats = categoryRepository.findAllById(idCategories);
        Page<Company> companies;
        if (isEdiCooperation == null && isApiCooperation == null && isProductFileCooperation==null) {
            companies = companyRepository.findAllByCategoriesIn(cats, pageable);
        } else {
            companies = companyRepository.findAllByCategoriesInAndCriteria(cats, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
        }

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }

    public Page<CompanyInCatalog> getCompanies2(List<String> idCategories, Pageable pageable) {
        Page<Company> companies = companyRepository.getAllCompanyToCatalog(idCategories, pageable);

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }
}
