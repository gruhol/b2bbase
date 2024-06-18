package pl.thinkdata.b2bbase.company.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;
import pl.thinkdata.b2bbase.company.utils.DatesUtils;

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

    public SubscriptionCompanyWithRequestDto(Long companyId, SubscriptionTypeEnum type, int year, PaymentTypeEnum paymentType) {
        this.companyId = companyId;
        this.type = type;
        this.year = year;
        this.paymentType = paymentType;
        this.now = new Date();
        this.nowPlusYear = DatesUtils.addYearToTime(now, year);
    }

    public SubscriptionCompanyWithRequestDto() {
        this.now = new Date();
        this.nowPlusYear = DatesUtils.addYearToTime(now, year);
    }
}
