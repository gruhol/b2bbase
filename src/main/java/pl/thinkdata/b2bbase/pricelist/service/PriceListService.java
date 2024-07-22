package pl.thinkdata.b2bbase.pricelist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.pricelist.model.PriceList;
import pl.thinkdata.b2bbase.pricelist.repository.PriceListRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.GET_PRICE_ERROR;

@Service
@RequiredArgsConstructor
public class PriceListService {

    private final PriceListRepository priceListRepository;
    private final MessageGenerator messageGenerator;

    public PriceList getPrice(String productName) {
        Date now = new Date();
        List<PriceList> prices = priceListRepository.findByProductNameAndStartDateBeforeAndEndDateAfter(productName, now , now);
        Optional<PriceList> promotionPrice = prices.stream()
                .filter(price -> price.isActive())
                .filter(price -> price.isPromotionPrice())
                .findFirst();
        if (promotionPrice.isPresent()) {
            return promotionPrice.get();
        }
        return prices.stream()
                .filter(price -> price.isActive())
                .filter(price -> !price.isPromotionPrice())
                .findFirst().orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(GET_PRICE_ERROR)));

    }
}
