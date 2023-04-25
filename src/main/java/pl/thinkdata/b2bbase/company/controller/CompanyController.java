package pl.thinkdata.b2bbase.company.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.service.CompanyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }
}
