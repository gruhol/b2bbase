package pl.thinkdata.b2bbase.company.validator.predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.service.CompanyService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegonNumberIsTakenPredicateTest {

    @InjectMocks
    RegonNumberIsTakenPredicate predicate;
    @Mock
    CompanyService companyService;

    @Test
    public void shouldReturnTrueIfNipIsTaken() {
        //given
        CompanyDto companyDto = new CompanyDto();
        companyDto.setRegon("972142820");
        //when
        when(companyService.findByRegon(companyDto.getRegon())).thenReturn(Boolean.TRUE);
        boolean result = predicate.test(companyDto);
        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfNipIsNotTaken() {
        //given
        CompanyDto companyDto = new CompanyDto();
        companyDto.setRegon("972142820");
        //when
        when(companyService.findByRegon(companyDto.getRegon())).thenReturn(Boolean.FALSE);
        boolean result = predicate.test(companyDto);
        //then
        assertFalse(result);
    }
}