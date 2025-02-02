package pl.thinkdata.b2bbase.discountcode.dto;

import lombok.*;
import pl.thinkdata.b2bbase.discountcode.enums.DiscountType;

import java.math.BigDecimal;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DiscountCodeResponse {
    private String code;
    private String subscriptionName;
    private DiscountType discountType;
    private BigDecimal discountAmount;
}
