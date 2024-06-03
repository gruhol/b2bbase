package pl.thinkdata.b2bbase.company.mapper;


import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;

import java.util.Date;

public class SubscriptionOrderMapper {

    public static SubscriptionOrder map(Long companyId, Date startDate, Date endDate, SubscriptionTypeEnum type, PaymentTypeEnum paymentType) {
        return SubscriptionOrder.builder()
                .companyId(companyId)
                .startDate(startDate)
                .endDate(endDate)
                .subscriptionType(type)
                .paymentStatus(PaymentStatusEnum.NOTPAID)
                .paymentType(paymentType)
                .build();
    }
}
