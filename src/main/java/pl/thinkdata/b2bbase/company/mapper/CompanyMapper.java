package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.dto.CompanyResponse;
import pl.thinkdata.b2bbase.company.model.Company;

public class CompanyMapper {

    public static Company mapToCompany(CompanyDto companyDto) {
        return Company.builder()
                .name(companyDto.getName())
                .slug(companyDto.getSlug())
                .type(companyDto.getType())
                .legalForm(companyDto.getLegalForm())
                .nip(companyDto.getNip())
                .regon(companyDto.getRegon())
                .krs(companyDto.getKrs())
                .email(companyDto.getEmail())
                .phone(companyDto.getPhone())
                .wwwSite(companyDto.getWwwSite())
                .wwwStore(companyDto.getWwwStore())
                .build();
    }

    public static CompanyResponse mapToCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .name(company.getName())
                .slug(company.getSlug())
                .type(company.getType())
                .legalForm(company.getLegalForm())
                .nip(company.getNip())
                .regon(company.getRegon())
                .krs(company.getKrs())
                .email(company.getEmail())
                .phone(company.getPhone())
                .wwwSite(company.getWwwSite())
                .wwwStore(company.getWwwStore())
                .build();
    }
}