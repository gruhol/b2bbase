package pl.thinkdata.b2bbase.company.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.service.SubscriptionOrderService;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class OtherSubscriptionIsActive<T extends SubscriptionCompanyDto> implements Predicate<SubscriptionCompanyDto> {

    private final SubscriptionOrderService subscriptionOrderService;

    @Override
    public boolean test(SubscriptionCompanyDto dto) {
        return !subscriptionOrderService
                .findActiveSubscription(dto.getCompanyId(), dto.getNow(), dto.getNowPlusYear()).isEmpty();
    }
}
