package pl.thinkdata.b2bbase.company.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.CompanyDictionary;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.company.validator.predicate.NipNumberIsTakenPredicate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegistrationValidator {

    public static final String error_message = "Validation error. The following fields contain a validation error.";

    private CompanyDto companyDto;
    private NipNumberIsTakenPredicate<CompanyDto> nipNumberIsTakenPredicate;
    private CompanyService companyService;

    public RegistrationValidator(CompanyDto companyDto, CompanyService companyService) {
        this.companyDto = companyDto;
        this.companyService = companyService;
        this.nipNumberIsTakenPredicate = new NipNumberIsTakenPredicate<>(companyService);
    }

    public void valid() {
        if (nipNumberIsTakenPredicate.test(companyDto)) {
            Map<String, String> fields = new HashMap<>();
            fields.put(CompanyDictionary.NIP, CompanyDictionary.NUMER_NIP_JEST_JUZ_ZAREJESTROWANY);
            throw new ValidationException(error_message, fields);
        }
    }
}
