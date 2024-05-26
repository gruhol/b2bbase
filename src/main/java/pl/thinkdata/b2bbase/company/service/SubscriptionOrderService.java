package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.model.enums.PaymentTypeEnum;
import pl.thinkdata.b2bbase.company.model.enums.SubscriptionTypeEnum;
import pl.thinkdata.b2bbase.company.repository.SubscriptionOrderRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.company.mapper.PackageOrderMapper.mapToSubscriptionOrderToCatalog;

@Service
@RequiredArgsConstructor
public class SubscriptionOrderService {

    private final CompanyService companyService;
    private final MessageGenerator messageGenerator;
    private final SubscriptionOrderRepository subscriptionOrderRepository;

    public List<SubscriptionOrderToCatalog> getCompanySubscription(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return subscriptionOrderRepository.findAllByCompanyId(company.getId()).stream()
                .map(order -> mapToSubscriptionOrderToCatalog(order))
                .collect(Collectors.toList());
    }

    public boolean createSubscriptionForCompany(Long companyId, SubscriptionTypeEnum type, int year, PaymentTypeEnum paymentType) {
        Date now = new Date();
        Calendar nowPlusOneYear = Calendar.getInstance();
        nowPlusOneYear.setTime(now);
        nowPlusOneYear.add(Calendar.YEAR, year);

        SubscriptionOrder subscriptionOrder =  SubscriptionOrder.builder()
                .companyId(companyId)
                .startDate(now)
                .endDate(nowPlusOneYear.getTime())
                .subscriptionType(type)
                .paymentStatus(PaymentStatusEnum.NOTPAID)
                .paymentType(paymentType)
                .build();
        subscriptionOrderRepository.save(subscriptionOrder);
        return true;
    }
}
