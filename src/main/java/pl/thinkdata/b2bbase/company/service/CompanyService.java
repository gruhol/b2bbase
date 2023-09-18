package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.CompanyDto;
import pl.thinkdata.b2bbase.company.dto.CompanyResponse;
import pl.thinkdata.b2bbase.company.mapper.CompanyMapper;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.CompanyRole;
import pl.thinkdata.b2bbase.company.model.UserRole2Company;
import pl.thinkdata.b2bbase.company.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.company.validator.RegistrationValidator;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_FROM_GIVEN_TOKEN_NOT_FOUND;
import static pl.thinkdata.b2bbase.company.mapper.CompanyMapper.mapToCompanyResponse;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private static final String TOKEN_HEADER = "Authorization";

    private final CompanyRepository companyRepository;
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;
    private final MessageGenerator messageGenerator;
    private final UserDetailsService userDetailsService;
    private final UserRole2CompanyRepository userRole2CompanyRepository;

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public CompanyResponse addCompany(CompanyDto companyDto, HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        RegistrationValidator registrationValidator = new RegistrationValidator(companyDto, this);
        registrationValidator.valid();

        String username = tokenUtil.validTokenAndGetUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername())).get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));

        Company newCompany = companyRepository.save(CompanyMapper.mapToCompany(companyDto));
        UserRole2Company userRole2Company = UserRole2Company.builder()
                .company_id(newCompany.getId())
                .role(CompanyRole.ADMIN)
                .user_id(user.getId())
                .build();
        userRole2CompanyRepository.save(userRole2Company);
        return mapToCompanyResponse(newCompany);
    }

    public boolean findByNip(String nip) {
        return companyRepository.findByNip(nip).isPresent();
    }

    public boolean findByRegon(String regon) {
        return companyRepository.findByRegon(regon).isPresent();
    }
}
