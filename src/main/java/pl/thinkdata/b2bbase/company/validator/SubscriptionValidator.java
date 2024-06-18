package pl.thinkdata.b2bbase.company.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.CompanyDictionary;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyWithRequestDto;
import pl.thinkdata.b2bbase.company.validator.predicate.OtherSubscriptionIsActive;
import pl.thinkdata.b2bbase.company.validator.predicate.UserIsAdminCompany;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SubscriptionValidator {

    public static final String error_message = "Validation subscription error. The following fields contain a validation error.";

    private final OtherSubscriptionIsActive<SubscriptionCompanyWithRequestDto> otherSubscriptionIsActive;
    private final UserIsAdminCompany<SubscriptionCompanyWithRequestDto> userIsAdminCompany;

    public void valid(SubscriptionCompanyWithRequestDto dto) {
        boolean error = false;
        Map<String, String> fields = new HashMap<>();
        if (otherSubscriptionIsActive.test(dto)) {
            error = true;
            fields.put(CompanyDictionary.SUBCRIPTION, CompanyDictionary.SUBCRIPTION_IS_ACTIVE);
        }
        if (userIsAdminCompany.test(dto)) {
            error = true;
            fields.put(CompanyDictionary.USER, CompanyDictionary.USER_IS_NO_ADMIN_OF_COMPANY);
        }
        if (error) {
            throw new ValidationException(error_message, fields);
        }
    }
}
