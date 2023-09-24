package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.AuthorizationException;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.BranchRepository;

import java.util.List;
import java.util.Optional;

import static pl.thinkdata.b2bbase.company.comonent.SlugGenerator.toSlug;

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

    public Branch addBranch(BranchDto branchDto, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        Branch branch = Branch.builder()
                .name(branchDto.getName())
                .headquarter(branchDto.isHeadquarter())
                .slug(toSlug(branchDto.getName()))
                .voivodeship(branchDto.getVoivodeship())
                .post_code(branchDto.getPost_code())
                .city(branchDto.getCity())
                .street(branchDto.getStreet())
                .house_number(branchDto.getHouse_number())
                .office_number(branchDto.getOffice_number())
                .email(branchDto.getEmail())
                .phone(branchDto.getPhone())
                .longitude(branchDto.getLongitude())
                .latitude(branchDto.getLatitude())
                .company(company)
                .build();
        return branchRepository.save(branch);
    }
}
