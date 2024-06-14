package pl.thinkdata.b2bbase.company.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;
import pl.thinkdata.b2bbase.company.utils.DatesUtils;

import java.util.Date;

@Getter
@Setter
public class SubscriptionCompanyDto {
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

    public SubscriptionCompanyDto(Long companyId, SubscriptionTypeEnum type, int year, PaymentTypeEnum paymentType) {
        this.companyId = companyId;
        this.type = type;
        this.year = year;
        this.paymentType = paymentType;
        this.now = new Date();
        this.nowPlusYear = DatesUtils.addYearToTime(now, year);
    }

    public SubscriptionCompanyDto() {
        this.now = new Date();
        this.nowPlusYear = DatesUtils.addYearToTime(now, year);
    }
}
