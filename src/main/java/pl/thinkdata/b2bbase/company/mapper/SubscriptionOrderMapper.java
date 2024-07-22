package pl.thinkdata.b2bbase.company.mapper;


import jakarta.servlet.http.HttpServletRequest;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyWithRequestDto;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;
import pl.thinkdata.b2bbase.company.utils.DatesUtils;

import java.util.Date;

public class SubscriptionOrderMapper {

    public static SubscriptionOrder map(Long companyId, SubscriptionTypeEnum subscriptionType, int year, PaymentTypeEnum paymentMethod) {
        Date date = new Date();
        return SubscriptionOrder.builder()
                .companyId(companyId)
                .startDate(date)
                .endDate(DatesUtils.addYearToTime(date, year))
                .subscriptionType(subscriptionType)
                .paymentStatus(PaymentStatusEnum.NOTPAID)
                .paymentType(paymentMethod)
                .build();
    }

    public static SubscriptionCompanyWithRequestDto map(SubscriptionCompanyDto dto, HttpServletRequest request) {
        Date date = new Date();
        return SubscriptionCompanyWithRequestDto.builder()
                .companyId(dto.getCompanyId())
                .type(dto.getSubscriptionType())
                .year(dto.getYear())
                .paymentType(dto.getPaymentType())
                .now(date)
                .nowPlusYear(DatesUtils.addYearToTime(date, dto.getYear()))
                .request(request)
                .build();
    }
}
