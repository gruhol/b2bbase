package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.repository.SubscriptionOrderRepository;
import pl.thinkdata.b2bbase.company.validator.SubscriptionValidator;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.company.mapper.PackageOrderMapper.mapToSubscriptionOrderToCatalog;
import static pl.thinkdata.b2bbase.company.mapper.SubscriptionOrderMapper.map;

@Service
@RequiredArgsConstructor
public class SubscriptionOrderService {

    private final CompanyService companyService;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final SubscriptionValidator subscriptionValidator;

    public List<SubscriptionOrderToCatalog> getCompanySubscription(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return subscriptionOrderRepository.findAllByCompanyId(company.getId()).stream()
                .map(order -> mapToSubscriptionOrderToCatalog(order))
                .collect(Collectors.toList());
    }

    public SubscriptionOrder createSubscriptionForCompany(SubscriptionCompanyDto dto, HttpServletRequest request) {

        subscriptionValidator.valid(map(dto, request));

        SubscriptionOrder subscription = subscriptionOrderRepository.save(
                map(dto.getCompanyId(), dto.getNow(), dto.getNowPlusYear(), dto.getType(), dto.getPaymentType()));
        return subscription;
    }

    public List<SubscriptionOrder> findActiveSubscription(Long companyId, Date startDate, Date endDate) {
        return subscriptionOrderRepository.findAllByCompanyId(companyId).stream()
                .filter(subscription -> subscription.getStartDate().before(startDate) && subscription.getEndDate().after(endDate))
                .filter(paymentStatus -> paymentStatus.getPaymentStatus() == (PaymentStatusEnum.PAID))
                .toList();
    }
}
