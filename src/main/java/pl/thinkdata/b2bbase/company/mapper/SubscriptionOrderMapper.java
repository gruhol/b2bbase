package pl.thinkdata.b2bbase.company.mapper;


import jakarta.servlet.http.HttpServletRequest;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyWithRequestDto;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;
import pl.thinkdata.b2bbase.company.utils.DatesUtils;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;

import java.util.Date;

public class SubscriptionOrderMapper {

    public static SubscriptionOrder map(SubscriptionCompanyDto dto, PriceList priceList) {
        Date date = new Date();
        return SubscriptionOrder.builder()
                .companyId(dto.getCompanyId())
                .startDate(date)
                .endDate(DatesUtils.addYearToTime(date, dto.getYear()))
                .subscriptionType(dto.getSubscriptionType())
                .price(priceList.getPrice())
                .paymentStatus(PaymentStatusEnum.NOTPAID)
                .paymentType(dto.getPaymentType())
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
