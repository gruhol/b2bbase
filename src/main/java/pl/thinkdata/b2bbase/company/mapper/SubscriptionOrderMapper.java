package pl.thinkdata.b2bbase.company.mapper;

import jakarta.servlet.http.HttpServletRequest;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyWithRequestDto;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.utils.DatesUtils;
import pl.thinkdata.b2bbase.discountcode.enums.DiscountType;
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

public class SubscriptionOrderMapper {

    public static SubscriptionOrder map(SubscriptionCompanyDto dto, PriceList priceList, Optional<DiscountCode> code) {
        Date date = new Date();
        BigInteger price = code.map(c -> getPriceWithDiscount(priceList, c))
                .orElse(priceList.getPrice());

        return SubscriptionOrder.builder()
                .companyId(dto.getCompanyId())
                .startDate(date)
                .endDate(DatesUtils.addYearToTime(date, dto.getYear()))
                .subscriptionType(dto.getSubscriptionType())
                .price(price)
                .paymentStatus(PaymentStatusEnum.NOTPAID)
                .paymentType(dto.getPaymentType())
                .build();
    }

    private  static BigInteger getPriceWithDiscount(PriceList priceList, DiscountCode code) {
        BigDecimal priceAsDecimal = new BigDecimal(priceList.getPrice());
        if (code.getDiscountType().equals(DiscountType.PRECENTAGE)) {
            BigDecimal diffrent = priceAsDecimal.multiply(code.getDiscountAmount());
            return priceAsDecimal.subtract(diffrent).toBigInteger();
        } else {
            BigDecimal subtract = priceAsDecimal.subtract(code.getDiscountAmount());
            return subtract.compareTo(BigDecimal.ZERO) < 0 ? BigInteger.valueOf(1L) : subtract.toBigInteger();
        }
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
