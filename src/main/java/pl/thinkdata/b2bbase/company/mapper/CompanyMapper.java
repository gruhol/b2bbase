package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.CompanyDto;
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
}