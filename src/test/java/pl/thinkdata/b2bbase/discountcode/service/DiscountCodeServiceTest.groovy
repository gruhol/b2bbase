package pl.thinkdata.b2bbase.discountcode.service

import org.apache.commons.lang3.time.DateUtils
import pl.thinkdata.b2bbase.common.util.MessageGenerator
import pl.thinkdata.b2bbase.discountcode.model.DiscountCode
import pl.thinkdata.b2bbase.discountcode.repository.DiscountCodeRepository
import spock.lang.Shared
import spock.lang.Specification

class DiscountCodeServiceTest extends Specification {

    @Shared
    private DiscountCode codeOk = setupCode("123", 2, false, true)

    def "Should get code form database"() {
        given:
            def discountCodeRepository = Mock(DiscountCodeRepository)
            def messageGenerator = Mock(MessageGenerator)
            def service = new DiscountCodeService(discountCodeRepository, messageGenerator)

        when:
        discountCodeRepository.findByCode(codeHash) >> responseCode
        def code = service.getDiscountCode(codeHash)

        then:
        codeName == code.getCode()

        where:
        codeHash | responseCode        | codeName
        "Dupa"   | Optional.of(codeOk) | "123"

    }

    def setupCode(String code, int usageLimit, boolean isExpired, boolean isActive) {
        Date date = new Date()
        Date startDate
        Date endDate
        if (isExpired) {
            startDate = DateUtils.addYears(date, -2)
            endDate = DateUtils.addYears(date, -1)
        } else {
            startDate = DateUtils.addYears(date, -1)
            endDate = DateUtils.addYears(date, 1)
        }
        return DiscountCode.builder()
                .code(code)
                .usageLimit(usageLimit)
                .startDate(startDate)
                .endDate(endDate)
                .isActive(isActive)
                .build()
    }
}
