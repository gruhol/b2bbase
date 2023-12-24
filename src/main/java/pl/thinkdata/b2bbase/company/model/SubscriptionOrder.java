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
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;

import java.util.Date;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    @Enumerated(EnumType.STRING)
    private SubscriptionTypeEnum subscriptionType;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentType;
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

}
