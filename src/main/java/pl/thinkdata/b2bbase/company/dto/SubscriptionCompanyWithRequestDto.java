package pl.thinkdata.b2bbase.company.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;

import java.util.Date;
@Getter
@Builder
@AllArgsConstructor
public class SubscriptionCompanyWithRequestDto {
    @NotNull
    private Long companyId;
    @NotNull
    private SubscriptionTypeEnum type;
    @NotNull
    private int year;
    @NotNull
    private PaymentTypeEnum paymentType;
    private Date now;
    private Date nowPlusYear;
    private HttpServletRequest request;
}
