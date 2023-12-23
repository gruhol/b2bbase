package pl.thinkdata.b2bbase.company.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.thinkdata.b2bbase.company.model.enums.PackageTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;

import java.util.Date;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PackageOrderToCatalog {
    private PackageTypeEnum packageType;
    private Date startDate;
    private Date endDate;
    private PaymentTypeEnum paymentType;
    private PaymentStatusEnum paymentStatus;
}
