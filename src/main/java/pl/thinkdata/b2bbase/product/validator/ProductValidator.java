package pl.thinkdata.b2bbase.product.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.ProductDictionary;
import pl.thinkdata.b2bbase.product.dto.ProductCompanyDto;
import pl.thinkdata.b2bbase.product.service.ProductService;
import pl.thinkdata.b2bbase.product.validator.predicate.EanNumerIsTakenPredicate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    public static final String error_message = "Product validation error. The following fields contain a validation error.";

    private ProductCompanyDto productCompanyDto;
    private ProductService productService;
    private EanNumerIsTakenPredicate eanNumerIsTakenPredicate;

    public ProductValidator(ProductCompanyDto productCompanyDto, ProductService productService) {
        this.productCompanyDto = productCompanyDto;
        this.productService = productService;
        this.eanNumerIsTakenPredicate = new EanNumerIsTakenPredicate<>(productService);
    }

    public void valid() {
        boolean error = false;
        Map<String, String> fields = new HashMap<>();
        if (eanNumerIsTakenPredicate.test(productCompanyDto)) {
            error = true;
            fields.put(ProductDictionary.EAN, ProductDictionary.EAN_NUMBER_IS_ALREADY_TAKEN);
        }
        if (error) {
            throw new ValidationException(error_message, fields);
        }
    }
}
