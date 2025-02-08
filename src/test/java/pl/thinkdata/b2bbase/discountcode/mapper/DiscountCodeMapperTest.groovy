package pl.thinkdata.b2bbase.discountcode.mapper

import org.apache.commons.lang3.time.DateUtils
import pl.thinkdata.b2bbase.discountcode.enums.DiscountType
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode
import spock.lang.Specification

class DiscountCodeMapperTest extends Specification {

    def "shoud map DiscountCode to DiscountCodeResponse"() {
        given:
        DiscountCode discountCode = setUpDiscountCode()
        when:
        def result = DiscountCodeMapper.map(discountCode)
        then:
        result.code == "XYZ123"
    }

    private static DiscountCode setUpDiscountCode() {
        Date date = DateUtils.parseDate("2025-02-08", "yyyy-MM-dd");
        Date dateYearLetter = DateUtils.addYears(date, 1)

        return DiscountCode.builder()
                .code("XYZ123")
                .subscriptionName("Basic")
                .discountType(DiscountType.PRECENTAGE)
                .discountAmount(0.23)
                .usageLimit(2)
                .startDate(date)
                .endDate(dateYearLetter)
                .isActive(true)
                .createdAt(date)
                .build()
    }
}
