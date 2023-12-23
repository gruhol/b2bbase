package pl.thinkdata.b2bbase.catalog.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogExtended;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalogWithCategory;
import pl.thinkdata.b2bbase.common.repository.CategoryRepository;
import pl.thinkdata.b2bbase.common.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.enums.VoivodeshipEnum;
import pl.thinkdata.b2bbase.company.repository.BranchRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalog;
import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalogExtended;
import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalogWithCategory;

@Service
@RequiredArgsConstructor
public class CatalogCompanyService {

    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final BranchRepository branchRepository;

    public Page<CompanyInCatalog> getCompaniesBySlug(String slug,
                                                     Boolean isEdiCooperation,
                                                     Boolean isApiCooperation,
                                                     Boolean isProductFileCooperation,
                                                     Pageable pageable) {
        List<Long> categories;
        List<VoivodeshipEnum> voivodeshipes = new ArrayList<>();
        Page<Company> companies;

        categories = categoryRepository.findBySlug(slug).stream()
                .map(category -> category.getId())
                .collect(Collectors.toList());
        if (findVoivodeshipEnumBySlug(slug) != null) {
            voivodeshipes = Arrays.asList(findVoivodeshipEnumBySlug(slug));
        }

        if (CollectionUtils.isNotEmpty(categories)) {
            companies = companyRepository.getAllCompanyByCategoryToCatalog(categories, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        } else if  (CollectionUtils.isNotEmpty(voivodeshipes)) {
            companies = companyRepository.getAllCompanyByVoivodeshipToCatalog(voivodeshipes,  isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        } else  {
            companies = companyRepository.getAllCompanyToCatalog(isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
        }

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .filter(Company::isActive)
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
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
                    .map(slug -> findVoivodeshipEnumBySlug(slug))
                    .collect(Collectors.toList());
        }

        Page<Company> companies = companyRepository.getAllCompanyByVoivodeshipAndCategoryToCatalog(categories,
                voivodeshipEnumList, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        companyInCatalogList.stream().forEach(company -> {
            Optional<Branch> branch = branchRepository.findByCompanyIdAndHeadquarter(company.getId(), true);
            company.setBranch(branch.isPresent() ? branch.get() : null );
        });

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }

    public CompanyInCatalogExtended getCompanyBySlug(String slug) {
        CompanyInCatalogExtended companyInCatalogExtended = mapToCompanyInCatalogExtended(companyRepository.findBySlugAndActive(slug, true).orElseThrow());
        Optional<Branch> branch = branchRepository.findByCompanyIdAndHeadquarter(companyInCatalogExtended.getId(), true);
        companyInCatalogExtended.setBranch(branch.isPresent() ? branch.get() : null );
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
                .map(company -> mapToCompanyInCatalogWithCategory(company)).collect(Collectors.toList());
    }
}
