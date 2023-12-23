package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.thinkdata.b2bbase.company.model.enums.PackageTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;

import java.util.Date;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PacketOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long CompanyId;
    @Enumerated(EnumType.STRING)
    private PackageTypeEnum packageType;
    private Date start_date;
    private Date end_date;
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentTypeEnum;
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatusEnum;

}
