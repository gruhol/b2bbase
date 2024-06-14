package pl.thinkdata.b2bbase.company.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.CompanyDictionary;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.service.SubscriptionOrderService;
import pl.thinkdata.b2bbase.company.validator.predicate.OtherSubscriptionIsActive;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SubscriptionValidator {

    public static final String error_message = "Validation subscription error. The following fields contain a validation error.";

    private SubscriptionOrderService subscriptionOrderService;
    private OtherSubscriptionIsActive<SubscriptionCompanyDto> otherSubscriptionIsActive;

    public SubscriptionValidator(SubscriptionOrderService subscriptionOrderService) {
        this.subscriptionOrderService = subscriptionOrderService;
        this.otherSubscriptionIsActive = new OtherSubscriptionIsActive<>(subscriptionOrderService);
    }

    public void valid(SubscriptionCompanyDto dto) {
        boolean error = false;
        Map<String, String> fields = new HashMap<>();
        if (otherSubscriptionIsActive.test(dto)) {
            error = true;
            fields.put(CompanyDictionary.NIP, CompanyDictionary.NIP_NUMBER_IS_ALREADY_REGISTERED);
        }
        if (error) {
            throw new ValidationException(error_message, fields);
        }
    }
}
