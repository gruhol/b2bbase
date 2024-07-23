package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;

public class PackageOrderMapper {

    public static SubscriptionOrderToCatalog mapToSubscriptionOrderToCatalog(SubscriptionOrder packageOrder) {
        return SubscriptionOrderToCatalog.builder()
                .id(packageOrder.getId())
                .packageType(packageOrder.getSubscriptionType())
                .price(packageOrder.getPrice())
                .startDate(packageOrder.getStartDate())
                .endDate(packageOrder.getEndDate())
                .paymentStatus(packageOrder.getPaymentStatus())
                .paymentType(packageOrder.getPaymentType())
                .build();
    }
}
