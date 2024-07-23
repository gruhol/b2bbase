package pl.thinkdata.b2bbase.company.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyWithRequestDto;
import pl.thinkdata.b2bbase.company.service.SubscriptionOrderService;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class OtherSubscriptionIsActive<T extends SubscriptionCompanyWithRequestDto> implements Predicate<SubscriptionCompanyWithRequestDto> {

    private SubscriptionOrderService subscriptionOrderService;

    @Autowired
    public OtherSubscriptionIsActive(@Lazy SubscriptionOrderService subscriptionOrderService) {
        this.subscriptionOrderService = subscriptionOrderService;
    }

    @Override
    public boolean test(SubscriptionCompanyWithRequestDto dto) {
        return !subscriptionOrderService
                .findActiveSubscription(dto.getCompanyId(), dto.getNow(), dto.getNowPlusYear()).isEmpty();
    }
}
