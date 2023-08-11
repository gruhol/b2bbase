package pl.thinkdata.b2bbase.security.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<PhoneValidation, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
