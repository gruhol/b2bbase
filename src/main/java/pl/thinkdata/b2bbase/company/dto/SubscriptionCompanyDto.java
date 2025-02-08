package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;

@Getter
@Setter
public class SubscriptionCompanyDto {
    @NotNull
    private Long companyId;
    @NotNull
    private SubscriptionTypeEnum subscriptionType;
    @NotNull
    private int year;
    @NotNull
    private PaymentTypeEnum paymentType;
    private String discountCode;
}
