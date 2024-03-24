package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.company.dto.*;
import pl.thinkdata.b2bbase.company.service.CompanyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/add")
    public CompanyResponse addCompany(@RequestBody @Valid CompanyDto companyDto, HttpServletRequest request) {
        return companyService.addCompany(companyDto, request);
    }

    @GetMapping("/user")
    public CompanyToEdit getCompanyByUser(HttpServletRequest request) {
        return companyService.getCompanyToEdit(request);
    }

    @PostMapping("/user")
    public CompanyToEdit editCompanyByUser(@RequestBody @Valid CompanyToEditDto companyToEdit, HttpServletRequest request) {
        return companyService.editCompany(companyToEdit, request);
    }

    @PostMapping("/edit-additional-data")
    public CompanyToEdit editAdditionalDataCompany(@RequestBody @Valid AdditionalDataToEdit additionalDataToEdit, HttpServletRequest request ) {
        return companyService.editAdditionalDataCompany(additionalDataToEdit, request);
    }
}
