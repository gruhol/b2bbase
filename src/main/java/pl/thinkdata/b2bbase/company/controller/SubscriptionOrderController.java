package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.dto.SubscriptionOrderToCatalog;
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
}
