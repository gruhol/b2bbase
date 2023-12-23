package pl.thinkdata.b2bbase.company.mapper;

import pl.thinkdata.b2bbase.company.dto.PackageOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.PackageOrder;

public class PackageOrderMapper {

    public static PackageOrderToCatalog mapToPackageOrderToCatalog(PackageOrder packageOrder) {
        return PackageOrderToCatalog.builder()
                .packageType(packageOrder.getPackageType())
                .startDate(packageOrder.getStartDate())
                .endDate(packageOrder.getEndDate())
                .paymentStatus(packageOrder.getPaymentStatus())
                .paymentType(packageOrder.getPaymentType())
                .build();
    }
}
