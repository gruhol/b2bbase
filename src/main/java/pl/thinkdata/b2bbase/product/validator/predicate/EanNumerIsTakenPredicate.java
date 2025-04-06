package pl.thinkdata.b2bbase.product.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.product.dto.ProductCompanyDto;
import pl.thinkdata.b2bbase.product.service.ProductService;

import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class EanNumerIsTakenPredicate<T extends ProductCompanyDto> implements Predicate<ProductCompanyDto> {

    private final ProductService productService;

    @Override
    public boolean test(ProductCompanyDto companyDto) {
        return productService.checkIfEanExistInProductCompany(companyDto.getEan());
    }
}

