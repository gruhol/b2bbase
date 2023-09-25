package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.company.dto.BranchDto;
import pl.thinkdata.b2bbase.company.dto.BranchResponse;
import pl.thinkdata.b2bbase.company.dto.BranchToEditDto;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.BranchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.WRONG_BRANCH_DATA;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.YOU_CAN_ADD_ONLY_ONE_HEADQUATER;
import static pl.thinkdata.b2bbase.company.comonent.SlugGenerator.toSlug;
import static pl.thinkdata.b2bbase.company.mapper.BranchMapper.mapToBranchFromBranchDto;
import static pl.thinkdata.b2bbase.company.mapper.BranchMapper.mapToBranchFromBranchToEditDto;
import static pl.thinkdata.b2bbase.company.mapper.BranchMapper.mapToBranchResponse;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyService companyService;
    private final MessageGenerator messageGenerator;

    public List<BranchResponse> getBranchByCompany(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        return branchRepository.findAllByCompany(company).stream()
                .map(branch -> mapToBranchResponse(branch))
                .collect(Collectors.toList());
    }

    public Branch addBranch(BranchDto branchDto, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        if (branchDto.isHeadquarter()) {
            boolean isHeadquarter = !branchRepository.findAllByCompany(company).stream()
                    .filter(branch -> branch.isHeadquarter() == true)
                    .collect(Collectors.toList())
                    .isEmpty();
            if (isHeadquarter) throw new InvalidRequestDataException(messageGenerator.get(YOU_CAN_ADD_ONLY_ONE_HEADQUATER));
        }
        Branch branch = mapToBranchFromBranchDto(branchDto, company);
        return branchRepository.save(branch);
    }

    public Branch editBranch(BranchToEditDto branchToEditDto, HttpServletRequest request) {
        Company company = companyService.getCompany(request);

        boolean isBranchCompany = branchRepository.findAllByCompany(company).stream()
                .map(idBranch -> idBranch.getId())
                .collect(Collectors.toList())
                .stream()
                .filter(d -> d == branchToEditDto.getId())
                .findAny().isPresent();

        if (!isBranchCompany) {
            throw new InvalidRequestDataException(messageGenerator.get(WRONG_BRANCH_DATA));
        }
        branchToEditDto.setSlug(checkIfSlugExistAndAddNumberToName(branchToEditDto.getName()));
        return branchRepository.save(mapToBranchFromBranchToEditDto(branchToEditDto, company));
    }

    public void deleteBranch(Long id, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        boolean isBranchCompany = branchRepository.findAllByCompany(company).stream()
                .map(idBranch -> idBranch.getId())
                .collect(Collectors.toList())
                .stream()
                .filter(d -> d == id)
                .findAny().isPresent();

        if (!isBranchCompany) {
            throw new InvalidRequestDataException(messageGenerator.get(WRONG_BRANCH_DATA));
        }
        branchRepository.deleteById(id);
    }

    private String checkIfSlugExistAndAddNumberToName(String name) {
        if (branchRepository.findBySlug(toSlug(name)).isPresent()) {
            char lastChar = name.charAt(name.length() - 1);
            return Character.isDigit(lastChar) ? toSlug(increaseBy1(name)) : toSlug(name + 1);
        }
        return toSlug(name);
    }

    private String increaseBy1(String name) {
        String lastChar = name.substring(name.length() - 1);
        int number = Integer.parseInt(lastChar) + 1;
        String slugWithoutNumber = name.substring(0, name.length() - 2);
        return slugWithoutNumber + number;
    }
}
