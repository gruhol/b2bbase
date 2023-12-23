package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.dto.PackageOrderToCatalog;
import pl.thinkdata.b2bbase.company.service.PackageOrderService;

import java.util.List;

@RestController
@RequestMapping("/package-order")
@RequiredArgsConstructor
public class PackageOrderController {

    private final PackageOrderService packageOrderService;

    @GetMapping("/getOrders")
    public List<PackageOrderToCatalog> getOrders(HttpServletRequest request) {
        return packageOrderService.getCompanyOrders(request);
    }
}
