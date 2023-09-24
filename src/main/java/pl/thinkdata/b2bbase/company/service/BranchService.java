package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.BranchRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final TokenUtil tokenUtil;
    private final CompanyService companyService;

    public List<Branch> getBranchByCompany(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return branchRepository.findAllByCompany(company);
    }
}
