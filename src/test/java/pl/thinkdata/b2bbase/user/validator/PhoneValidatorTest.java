package pl.thinkdata.b2bbase.user.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.thinkdata.b2bbase.common.validator.PhoneValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneValidatorTest {

    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @Before("")
    public void setup() {
        context = Mockito.mock(ConstraintValidatorContext.class);
        builder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
        Mockito.when(context.buildConstraintViolationWithTemplate(Mockito.anyString()))
                .thenReturn(builder);
    }

    @Test
    public void shouldReturnTrueWhenPhoneNumberContainsOnlyNumber() {
        //given
        PhoneValidator phoneValidator = new PhoneValidator();
        String phoneNumber = "1234567890";
        //when
        Boolean result = phoneValidator.isValid(phoneNumber, context);
        //then
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenPhoneNumberNotContainsOnlyNumber() {
        //given
        PhoneValidator phoneValidator = new PhoneValidator();
        String phoneNumber = "123-4567890";
        //when
        Boolean result = phoneValidator.isValid(phoneNumber, context);
        //then
        assertFalse(result);
    }

}