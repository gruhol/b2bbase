package pl.thinkdata.b2bbase.company.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.company.dto.AbstractCompany;
import pl.thinkdata.b2bbase.company.service.CompanyService;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class RegonNumberIsTakenPredicate<T extends AbstractCompany> implements Predicate<AbstractCompany> {

    private final CompanyService companyService;

    @Override
    public boolean test(AbstractCompany companyDto) {
        return companyService.findByRegon(companyDto.getRegon());
    }
}
