package pl.thinkdata.b2bbase.pricelist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;
import pl.thinkdata.b2bbase.pricelist.service.PriceListService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pricelist")
public class PriceListController {

    private final PriceListService priceListService;

    @GetMapping("/getPrice/{productName}")
    public PriceList getProductPrice(@PathVariable String productName) {
        return priceListService.getPrice(productName);
    }
}
