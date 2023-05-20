package pl.thinkdata.b2bbase.company.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.service.CompanyService;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class RegonNumberIsTakenPredicate<T extends CompanyDto> implements Predicate<CompanyDto> {

    private final CompanyService companyService;

    @Override
    public boolean test(CompanyDto companyDto) {
        return companyService.findByRegon(companyDto.getRegon());
    }
}
