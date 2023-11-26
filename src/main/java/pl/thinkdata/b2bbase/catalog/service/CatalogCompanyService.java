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
import pl.thinkdata.b2bbase.company.model.VoivodeshipEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.catalog.mapper.CatalogMapper.mapToCompanyInCatalog;

@Service
@RequiredArgsConstructor
public class CatalogCompanyService {

    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public Page<CompanyInCatalog> getCompanies(String slug,
                                               Boolean isEdiCooperation,
                                               Boolean isApiCooperation,
                                               Boolean isProductFileCooperation,
                                               Pageable pageable) {
        List<Long> categories = new ArrayList<>();
        List<String> voivodeshipes = new ArrayList<>();
        Page<Company> companies;

        categories = categoryRepository.findBySlug(slug).stream()
                .map(company -> company.getId())
                .collect(Collectors.toList());
        if (findVoivodeshipEnumBySlug(slug) != null) {
            voivodeshipes = Arrays.asList(findVoivodeshipEnumBySlug(slug).name());
        }

        if (categories.size() > 0) {
            companies = companyRepository.getAllCompanyByCategoryToCatalog(categories, isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        } else if  (voivodeshipes.size() > 0) {
            companies = companyRepository.getAllCompanyByVoivodeshipToCatalog(voivodeshipes,  isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);

        } else  {
            companies = companyRepository.getAllCompanyToCatalog(isEdiCooperation, isApiCooperation, isProductFileCooperation, pageable);
        }

        List<CompanyInCatalog> companyInCatalogList = companies.stream()
                .map(company -> mapToCompanyInCatalog(company))
                .collect(Collectors.toList());

        return new PageImpl<>(companyInCatalogList, pageable, companies.getTotalElements());
    }

    public static VoivodeshipEnum findVoivodeshipEnumBySlug(String searchSlug) {
        if (searchSlug == null) return null;
        return Arrays.stream(VoivodeshipEnum.values())
                .filter(voivodeshipEnum -> voivodeshipEnum.getSlug().toLowerCase().equals(searchSlug.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
