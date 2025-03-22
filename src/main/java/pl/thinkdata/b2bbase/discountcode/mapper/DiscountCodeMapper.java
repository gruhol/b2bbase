package pl.thinkdata.b2bbase.discountcode.mapper;

import pl.thinkdata.b2bbase.discountcode.dto.DiscountCodeResponse;
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode;

public class DiscountCodeMapper {

    public static DiscountCodeResponse map(DiscountCode code) {
        return DiscountCodeResponse.builder()
                .code(code.getCode())
                .subscriptionName(code.getSubscriptionName())
                .discountType(code.getDiscountType())
                .discountAmount(code.getDiscountAmount())
                .build();
    }
}
