package pl.thinkdata.b2bbase.search.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CategoryToCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.common.repository.BranchRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.search.dto.SearchCompanyResult;

import java.util.*;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCategoryToCatalog;
import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalog;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CompanyRepository companyRepository;
    private final BranchRepository branchRepository;

    public SearchCompanyResult searchCompanyWithCategoryAndVoivodeshipByKeyword(String keyword, Pageable pageable) {
        Page<Company> companies = companyRepository.searchByKeyword(keyword.toLowerCase(), pageable);

        List<CompanyInCatalog> companyInCatalogList = companies.getContent().stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());
        companyInCatalogList.stream().forEach(company -> {
            Optional<Branch> branch = branchRepository.findByCompanyIdAndHeadquarter(company.getId(), true);
            company.setBranch(branch.isPresent() ? branch.get() : null );
        });
        Page<CompanyInCatalog> pageCompanyList = new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());

        Set<CategoryToCatalog> categoryListForCompany = getCategoryToCatalogListFromCompany(companies);
        Map<String, String> voivodeshipEnumList = createVoivodeshipEnumListFromCompanies(companies);

        return SearchCompanyResult.builder()
                .companies(pageCompanyList)
                .categoryListForCompany(categoryListForCompany)
                .voivodeshipEnumList(voivodeshipEnumList)
                .build();
    }

    @NotNull
    private static Set<CategoryToCatalog> getCategoryToCatalogListFromCompany(Page<Company> companies) {
        List<Category> categories = companies.stream()
                .flatMap(company -> company.getCategories().stream())
                .collect(Collectors.toList());
        Set<CategoryToCatalog> categoryListForCompany = new HashSet<>();
        companies.stream()
                .flatMap(company -> company.getCategories().stream())
                .filter(category -> category.getParent() == null)
                .forEach(category -> categoryListForCompany.add(mapToCategoryToCatalog(category, true, categories)));
        return categoryListForCompany;
    }

    @NotNull
    private static Map<String, String> createVoivodeshipEnumListFromCompanies(Page<Company> companies) {
        Map<String, String> mapVoivodeshipList = new HashMap<>();
        companies.stream()
                .flatMap(company -> company.getBranches().stream())
                .filter(bramch -> bramch.isHeadquarter())
                .forEach(branch -> mapVoivodeshipList.put(branch.getVoivodeship().getSlug(), branch.getVoivodeship().getValue()));
        return mapVoivodeshipList;
    }


}
