package pl.thinkdata.b2bbase.discountcode.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode;
import pl.thinkdata.b2bbase.discountcode.repository.DiscountCodeRepository;

import java.util.Date;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.CODE_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class DiscountCodeService {

    private final DiscountCodeRepository discountCodeRepository;
    private final MessageGenerator messageGenerator;

    public DiscountCode getDiscountCode(String code) {
        Date now = new Date();
        return discountCodeRepository.findByCode(code)
                .filter(limit -> limit.getUsage_limit() > 0)
                .filter(notExpired -> notExpired.isNotExpired(now))
                .filter(DiscountCode::isActive)
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(CODE_NOT_FOUND)));
    }
}
