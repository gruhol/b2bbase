package pl.thinkdata.b2bbase.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.product.dto.ProductCompanyDto;
import pl.thinkdata.b2bbase.product.model.ProductCompany;
import pl.thinkdata.b2bbase.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductCompany getProductCompany(String slug) {
        return null; //TODO
    }

    public List<ProductCompany> getProductsCompany(long companyId) {
        return null; //TODO
    }

    @PostMapping("/add")
    public ProductCompany addProductCompany(@RequestBody @Valid ProductCompanyDto productCompanyDto, HttpServletRequest request) {
        return productService.addProductService(productCompanyDto, request);
    }

    public ProductCompany editProductCompany(ProductCompany productCompany) {
        return null; //TODO
    }

    public boolean deletedProductCompany(long productId) {
        return false; //TODO
    }

}
