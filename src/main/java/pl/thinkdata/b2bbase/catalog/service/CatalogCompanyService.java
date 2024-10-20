package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CategoriesWithCompanies;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogExtended;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogWithCategory;
import pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper;
import pl.thinkdata.b2bbase.catalog.mapper.CategoryMapper;
import pl.thinkdata.b2bbase.common.repository.BranchRepository;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.enums.VoivodeshipEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalogExtended;

@Service
@RequiredArgsConstructor
public class CatalogCompanyService {

    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final BranchRepository branchRepository;

    public CategoriesWithCompanies getCompaniesBySlug(String slug,
                                                     Boolean isEdiCooperation,
                                                     Boolean isApiCooperation,
                                                     Boolean isProductFileCooperation,
                                                     Pageable pageable) {
        List<VoivodeshipEnum> voivodeshipes = new ArrayList<>();
        Page<Company> companies;

        List<Category> categories = categoryRepository.findBySlug(slug);
        categories.addAll(
                categories.stream()
                        .map(categoryRepository::findByParent)
                        .flatMap(List::stream)
                        .toList()
        );

        List<Long> categoriesIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());


        if (findVoivodeshipEnumBySlug(slug) != null) {
            voivodeshipes = Arrays.asList(findVoivodeshipEnumBySlug(slug));
        }

        if (CollectionUtils.isNotEmpty(categoriesIds)) {
            companies = companyRepository.getAllCompanyByCategoryToCatalog(categoriesIds, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        } else if  (CollectionUtils.isNotEmpty(voivodeshipes)) {
            companies = companyRepository.getAllCompanyByVoivodeshipToCatalog(voivodeshipes,  isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        } else  {
            companies = companyRepository.getAllCompanyToCatalog(isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
        }

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .filter(Company::isActive)
                .map(CatalogMapper::mapToCompanyInCatalog)
                .collect(Collectors.toList());
        return CategoriesWithCompanies.builder()
                .category(categories.stream()
                        .findFirst()
                        .map(CategoryMapper::map)
                        .orElse(null)
                )
                .listCompany(new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements()))
                .build();
    }

    private VoivodeshipEnum findVoivodeshipEnumBySlug(String searchSlug) {
        if (searchSlug == null) return null;
        return Arrays.stream(VoivodeshipEnum.values())
                .filter(voivodeshipEnum -> voivodeshipEnum.getSlug().toLowerCase().equals(searchSlug.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public Page<CompanyInCatalog> getCompanies(List<Long> categories,
                                               List<String> voivodeshipSlugs,
                                               Boolean isEdiCooperation,
                                               Boolean isApiCooperation,
                                               Boolean isProductFileCooperation,
                                               Pageable pageable) {
        List<VoivodeshipEnum> voivodeshipEnumList = null;
        if (voivodeshipSlugs != null) {
            voivodeshipEnumList = voivodeshipSlugs.stream()
                    .map(this::findVoivodeshipEnumBySlug)
                    .collect(Collectors.toList());
        }

        Page<Company> companies = companyRepository.getAllCompanyByVoivodeshipAndCategoryToCatalog(categories,
                voivodeshipEnumList, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(CatalogMapper::mapToCompanyInCatalog)
                .collect(Collectors.toList());

        companyInCatalogList.stream().forEach(company -> {
            Optional<Branch> branch = branchRepository.findByCompanyIdAndHeadquarter(company.getId(), true);
            company.setBranch(branch.orElse(null));
        });

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }

    public CompanyInCatalogExtended getCompanyBySlug(String slug) {
        CompanyInCatalogExtended companyInCatalogExtended = mapToCompanyInCatalogExtended(companyRepository.findBySlugAndActive(slug, true).orElseThrow());
        Optional<Branch> branch = branchRepository.findByCompanyIdAndHeadquarter(companyInCatalogExtended.getId(), true);
        companyInCatalogExtended.setBranch(branch.orElse(null));
        return companyInCatalogExtended;
    }

    public List<CompanyInCatalogWithCategory> getLastCompanies(Integer howMany) {
        Pageable pageable;
        if (howMany != null) {
            pageable = PageRequest.of(0, howMany, Sort.by("created").descending());
        } else {
            pageable = PageRequest.of(0, 10, Sort.by("created").descending());
        }

        return companyRepository.findAllByOrderByCreatedDesc(pageable).stream()
                .filter(Company::isActive)
                .map(CatalogMapper::mapToCompanyInCatalogWithCategory)
                .collect(Collectors.toList());
    }


}
