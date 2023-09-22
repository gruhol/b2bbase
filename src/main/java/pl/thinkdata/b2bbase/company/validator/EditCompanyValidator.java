package pl.thinkdata.b2bbase.company.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.ValidationException;
import pl.thinkdata.b2bbase.common.tool.CompanyDictionary;
import pl.thinkdata.b2bbase.company.dto.CompanyToEditDto;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.company.validator.predicate.NipNumberIsTakenPredicate;
import pl.thinkdata.b2bbase.company.validator.predicate.RegonNumberIsTakenPredicate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EditCompanyValidator {

    public static final String error_message = "Validation error. The following fields contain a validation error.";

    private CompanyToEditDto companyToEditDto;
    private NipNumberIsTakenPredicate<CompanyToEditDto> nipNumberIsTakenPredicate;
    private RegonNumberIsTakenPredicate<CompanyToEditDto> regonNumberIsTakenPredicate;
    private CompanyService companyService;
    private Company oldCompany;

    public EditCompanyValidator(CompanyToEditDto companyToEditDtoDto, Company oldCompany, CompanyService companyService) {
        this.companyToEditDto = companyToEditDtoDto;
        this.companyService = companyService;
        this.oldCompany = oldCompany;
        this.nipNumberIsTakenPredicate = new NipNumberIsTakenPredicate<>(companyService);
        this.regonNumberIsTakenPredicate = new RegonNumberIsTakenPredicate<>(companyService);
    }

    public void valid() {
        boolean error = false;
        Map<String, String> fields = new HashMap<>();
        if (!companyToEditDto.getNip().equals(oldCompany.getNip()) && nipNumberIsTakenPredicate.test(companyToEditDto)) {
            error = true;
            fields.put(CompanyDictionary.NIP, CompanyDictionary.NIP_NUMBER_IS_ALREADY_REGISTERED);
        }
        if (!companyToEditDto.getRegon().equals(oldCompany.getRegon()) && regonNumberIsTakenPredicate.test(companyToEditDto)) {
            error = true;
            fields.put(CompanyDictionary.REGON, CompanyDictionary.REGON_NUMBER_IS_ALREADY_REGISTERED);
        }
        if (error) {
            throw new ValidationException(error_message, fields);
        }
    }
}
