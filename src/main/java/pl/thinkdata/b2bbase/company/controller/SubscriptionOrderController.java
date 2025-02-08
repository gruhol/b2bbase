package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
import pl.thinkdata.b2bbase.company.model.SubscriptionOrder;
import pl.thinkdata.b2bbase.company.service.SubscriptionOrderService;

import java.util.List;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionOrderController {

    private final SubscriptionOrderService subscriptionOrderService;

    @GetMapping("/getOrders")
    public List<SubscriptionOrderToCatalog> getSubscription(HttpServletRequest request) {
        return subscriptionOrderService.getCompanySubscription(request);
    }

    @PostMapping("/add")
    public SubscriptionOrder addSubscriptionToCompany(@RequestBody @Valid SubscriptionCompanyDto subscriptionCompanyDto, HttpServletRequest request) {
        return subscriptionOrderService.createSubscriptionForCompany(subscriptionCompanyDto, request);
    }
}
