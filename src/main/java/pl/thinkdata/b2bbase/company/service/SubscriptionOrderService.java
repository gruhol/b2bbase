package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.repository.SubscriptionOrderRepository;
import pl.thinkdata.b2bbase.company.validator.SubscriptionValidator;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;
import static pl.thinkdata.b2bbase.company.mapper.PackageOrderMapper.mapToSubscriptionOrderToCatalog;
import static pl.thinkdata.b2bbase.company.mapper.SubscriptionOrderMapper.map;

@Service
@RequiredArgsConstructor
public class SubscriptionOrderService {

    private final CompanyService companyService;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final SubscriptionValidator subscriptionValidator;
    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public List<SubscriptionOrderToCatalog> getCompanySubscription(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return subscriptionOrderRepository.findAllByCompanyId(company.getId()).stream()
                .map(order -> mapToSubscriptionOrderToCatalog(order))
                .collect(Collectors.toList());
    }

    public SubscriptionOrder createSubscriptionForCompany(SubscriptionCompanyDto dto, HttpServletRequest request) {
        subscriptionValidator.valid(map(dto, request));
        deleteNotActiveSubscription(dto);
        SubscriptionOrder subscription = subscriptionOrderRepository.save(
                map(dto.getCompanyId(), dto.getSubscriptionType(), dto.getYear(), dto.getPaymentType()));
        sendConformEmail(request, subscription);
        return subscription;
    }

    private void sendConformEmail(HttpServletRequest request, SubscriptionOrder subscription) {
        String token = request.getHeader(TOKEN_HEADER);
        String username = tokenUtil.validTokenAndGetUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        //user
        //companyName
        //companyType
        //nip
        //regon
        //krs
        //phone
        //email
        //subscriptionType
        //price
        //startDate i andDate
        //bankAccount
        //orderId
    }

    private void deleteNotActiveSubscription(SubscriptionCompanyDto dto) {
        List<Long> subscriptionToDelete = subscriptionOrderRepository.findAllByCompanyId(dto.getCompanyId()).stream()
                .filter(paymentStatus -> paymentStatus.getPaymentStatus() == PaymentStatusEnum.NOTPAID)
                .map(SubscriptionOrder::getId)
                .toList();
        subscriptionOrderRepository.deleteAllById(subscriptionToDelete);
    }

    public List<SubscriptionOrder> findActiveSubscription(Long companyId, Date startDate, Date endDate) {
        return subscriptionOrderRepository.findAllByCompanyId(companyId).stream()
                .filter(subscription -> subscription.getStartDate().before(startDate) && subscription.getEndDate().after(endDate))
                .filter(paymentStatus -> paymentStatus.getPaymentStatus() == (PaymentStatusEnum.PAID))
                .toList();
    }
}
