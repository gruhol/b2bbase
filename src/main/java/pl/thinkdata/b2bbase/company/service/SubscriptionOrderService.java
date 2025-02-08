package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.thinkdata.b2bbase.common.service.emailSenderService.EmailSenderService;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.mapper.PackageOrderMapper;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.model.enums.PaymentStatusEnum;
import pl.thinkdata.b2bbase.company.repository.SubscriptionOrderRepository;
import pl.thinkdata.b2bbase.company.validator.SubscriptionValidator;
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode;
import pl.thinkdata.b2bbase.discountcode.repository.DiscountCodeRepository;
import pl.thinkdata.b2bbase.preferences.service.PreferencesService;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;
import pl.thinkdata.b2bbase.pricelist.service.PriceListService;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;
import static pl.thinkdata.b2bbase.company.mapper.SubscriptionOrderMapper.map;

@Service
@RequiredArgsConstructor
public class SubscriptionOrderService {

    public static final String BANK_ACCOUNT = "bank_account";
    public static final String SUBSCRIPTION = "SUBSCRIPTION_";
    public static final String COMPANY_REGISTRATION_CONFIRMATION = "company.registration.confirmation";

    private final CompanyService companyService;
    private final SubscriptionOrderRepository subscriptionOrderRepository;
    private final SubscriptionValidator subscriptionValidator;
    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    private final PriceListService priceListService;
    private final PreferencesService preferencesService;
    private final MessageGenerator messageGenerator;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DiscountCodeRepository discountCodeRepository;

    public List<SubscriptionOrderToCatalog> getCompanySubscription(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return subscriptionOrderRepository.findAllByCompanyId(company.getId()).stream()
                .map(PackageOrderMapper::mapToSubscriptionOrderToCatalog)
                .toList();
    }

    public SubscriptionOrder createSubscriptionForCompany(SubscriptionCompanyDto dto, HttpServletRequest request) {
        subscriptionValidator.valid(map(dto, request));
        deleteNotActiveSubscription(dto);

        Optional<DiscountCode> discountCode = getDiscountCodeIfExist(dto.getDiscountCode());

        PriceList priceList = priceListService.getPrice(SUBSCRIPTION + dto.getSubscriptionType());
        SubscriptionOrder subscription = subscriptionOrderRepository.save(
                map(dto, priceList, discountCode));
        discountCode.ifPresent(this::usedCode);
        sendConformEmail(request, subscription, priceList);
        return subscription;
    }

    public List<SubscriptionOrder> findActiveSubscription(Long companyId, Date startDate, Date endDate) {
        return subscriptionOrderRepository.findAllByCompanyId(companyId).stream()
                .filter(subscription -> subscription.getStartDate().before(startDate) && subscription.getEndDate().after(endDate))
                .filter(paymentStatus -> paymentStatus.getPaymentStatus() == (PaymentStatusEnum.PAID))
                .toList();
    }

    private Optional<DiscountCode> getDiscountCodeIfExist(String code) {
        if (Strings.isNotEmpty(code)) {
            return discountCodeRepository.findByCode(code)
                    .filter(limit -> limit.getUsage_limit() > 0)
                    .filter(notExpired -> notExpired.isNotExpired(new Date()))
                    .filter(DiscountCode::isActive);
        }
        return Optional.empty();
    }

    private void usedCode(DiscountCode discountCode) {
        int usageLimit = discountCode.getUsage_limit();
        discountCode.setUsage_limit(usageLimit - 1);
        discountCodeRepository.save(discountCode);
    }

    private void sendConformEmail(HttpServletRequest request, SubscriptionOrder subscription, PriceList priceList) {
        String token = request.getHeader(TOKEN_HEADER);
        String username = tokenUtil.validTokenAndGetUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String userName = userRepository.findByUsername(userDetails.getUsername()).get().getUsername();
        Company company = tokenUtil.getCompanyByUsernameFormDataBase(username);
        String bankAccount = preferencesService.getPerference(BANK_ACCOUNT);
        String dataLink = createDate(userName, company, subscription, priceList, bankAccount);
        String subject = messageGenerator.get(COMPANY_REGISTRATION_CONFIRMATION) + ": " + company.getName();
        Context context = getContext(company, subscription, priceList, bankAccount);
        String body = templateEngine.process("email-templates/registration-company-confirmation", context);
        emailSenderService.sendEmail(userName, subject, body, dataLink);
    }

    private String createDate(String userName, Company company, SubscriptionOrder subscription, PriceList price, String bankAccount) {
        return "User email: " + userName + " | " +
        "CompanyName: " + company.getName() + " | " +
        "CompanyType: " + company.getType() + " | " +
        "Nip: " + company.getNip() + " | " +
        "Regon: " + company.getRegon() + " | " +
        "KRS: " + company.getKrs() + " | " +
        "Phone: " + company.getPhone() + " | " +
        "Email: " + company.getEmail()  + " | " +
        "SubscriptionType: " + subscription.getSubscriptionType() + " | "  +
        "OrderId: " + subscription.getId() + " | "  +
        "StartDate: " + dateFormat.format(subscription.getStartDate()) + " | " +
        "EndData: " + dateFormat.format(subscription.getEndDate()) + " | " +
        "Price: " + price.getPrice() + " | " +
        "Bank Account: " + bankAccount;
    }

    private Context getContext(Company company, SubscriptionOrder subscription, PriceList price, String bankAccount) {
        Context context = new Context();
        context.setVariable("companyName", company.getName());
        context.setVariable("legalForm", company.getLegalForm());
        context.setVariable("nip", company.getNip());
        context.setVariable("regon", company.getRegon());
        context.setVariable("krs", company.getKrs());
        context.setVariable("phone", company.getPhone());
        context.setVariable("email", company.getEmail());
        context.setVariable("subscriptionType", subscription.getSubscriptionType());
        context.setVariable("price", price.getPrice());
        context.setVariable("startDate", dateFormat.format(subscription.getStartDate()));
        context.setVariable("endDate", dateFormat.format(subscription.getEndDate()));
        context.setVariable("orderId",  subscription.getId());
        context.setVariable("bankAccount",  bankAccount);
        return context;
    }

    private void deleteNotActiveSubscription(SubscriptionCompanyDto dto) {
        List<Long> subscriptionToDelete = subscriptionOrderRepository.findAllByCompanyId(dto.getCompanyId()).stream()
                .filter(paymentStatus -> paymentStatus.getPaymentStatus() == PaymentStatusEnum.NOTPAID)
                .map(SubscriptionOrder::getId)
                .toList();
        subscriptionOrderRepository.deleteAllById(subscriptionToDelete);
    }
}
