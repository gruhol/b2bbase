package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.SubscriptionOrderRepository;

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
}
