package pl.thinkdata.b2bbase.company.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.thinkdata.b2bbase.company.service.CompanyService;

public class UniqueNipValidator implements ConstraintValidator<UniqueNip, String> {

    private CompanyService repository;

    @Override
    public void initialize(UniqueNip constraintAnnotation) {
        repository = ServiceUtils.getCompanyService();
    }

    @Override
    public boolean isValid(String nip, ConstraintValidatorContext constraintValidatorContext) {
        return repository.nipExist(nip);
    }

}