package pl.thinkdata.b2bbase.company.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.CompanyDictionary;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.company.validator.predicate.NipNumberIsTakenPredicate;
import pl.thinkdata.b2bbase.company.validator.predicate.RegonNumberIsTakenPredicate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegistrationValidator {

    public static final String error_message = "Validation error. The following fields contain a validation error.";

    private CompanyDto companyDto;
    private NipNumberIsTakenPredicate<CompanyDto> nipNumberIsTakenPredicate;
    private RegonNumberIsTakenPredicate<CompanyDto> regonNumberIsTakenPredicate;
    private CompanyService companyService;

    public RegistrationValidator(CompanyDto companyDto, CompanyService companyService) {
        this.companyDto = companyDto;
        this.companyService = companyService;
        this.nipNumberIsTakenPredicate = new NipNumberIsTakenPredicate<>(companyService);
        this.regonNumberIsTakenPredicate = new RegonNumberIsTakenPredicate<>(companyService);
    }

    public void valid() {
        boolean error = false;
        Map<String, String> fields = new HashMap<>();
        if (nipNumberIsTakenPredicate.test(companyDto)) {
            error = true;
            fields.put(CompanyDictionary.NIP, CompanyDictionary.NIP_NUMBER_IS_ALREADY_REGISTERED);
        }
        if (regonNumberIsTakenPredicate.test(companyDto)) {
            error = true;
            fields.put(CompanyDictionary.REGON, CompanyDictionary.REGON_NUMBER_IS_ALREADY_REGISTERED);
        }
        if (error) {
            throw new ValidationException(error_message, fields);
        }
    }
}
