package pl.thinkdata.b2bbase.company.validator;

import lombok.RequiredArgsConstructor;
import pl.thinkdata.b2bbase.common.error.ValidationErrors;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.validator.predicate.NipNumberIsTakenPredicate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class RegistrationValidator {

    public static final String error_message = "Validation error. The following fields contain a validation error.";

    private CompanyDto companyDto;
    private NipNumberIsTakenPredicate<CompanyDto> nipNumberIsTakenPredicate;

    public void valid() {
        if (nipNumberIsTakenPredicate.test(companyDto)) {
            Map<String, String> fileds = new HashMap<>();
            fileds.put("nip", "Numer nip jest już zarejestrowany.");
            throw new ValidationErrors(error_message, fileds);
        }
    }
}
