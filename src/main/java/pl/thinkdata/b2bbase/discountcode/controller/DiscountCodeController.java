package pl.thinkdata.b2bbase.discountcode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode;
import pl.thinkdata.b2bbase.discountcode.service.DiscountCodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class DiscountCodeController {

    private final DiscountCodeService discountCodeService;

    @GetMapping("/{code}")
    public DiscountCode addCompany(@PathVariable String code) {
        return discountCodeService.getDiscountCode(code);
    }
}
