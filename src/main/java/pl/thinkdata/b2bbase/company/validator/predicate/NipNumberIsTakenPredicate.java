package pl.thinkdata.b2bbase.company.validator.predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.repository.CompanyRepository;

import java.util.function.Predicate;

@Component
public class NipNumberIsTakenPredicate<T extends CompanyDto> implements Predicate<CompanyDto> {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public boolean test(CompanyDto companyDto) {
        return companyRepository.findByNip(companyDto.getNip());
    }
}
