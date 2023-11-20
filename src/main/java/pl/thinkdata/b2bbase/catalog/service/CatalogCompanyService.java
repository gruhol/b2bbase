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

    public Page<CompanyInCatalog> getCompanies(List<Long> idCategories, Pageable pageable) {
        //Page<Company> companies = companyRepository.findAll(pageable);
        List<Category> cats = categoryRepository.findAllById(idCategories);

        Page<Company> companies = companyRepository.findAllByCategoriesIn(cats, pageable);
        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }

    public Page<CompanyInCatalog2> getCompanies2(List<String> idCategories, List<String> idBranches, Pageable pageable) {
        String dupa = "dupa";
        return companyRepository.getAllCompanyToCatalog(idCategories, idBranches, pageable);
    }
}
