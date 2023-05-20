package pl.thinkdata.b2bbase.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.mapper.CompanyMapper;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.validator.RegistrationValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company addCompany(CompanyDto companyDto) {
        RegistrationValidator registrationValidator = new RegistrationValidator(companyDto, this);
        registrationValidator.valid();
        return companyRepository.save(CompanyMapper.mapToCompany(companyDto));
    }

    public boolean findByNip(String nip) {
        return companyRepository.findByNip(nip).isPresent();
    }

    public boolean findByRegon(String regon) {
        return companyRepository.findByRegon(regon).isPresent();
    }
}
