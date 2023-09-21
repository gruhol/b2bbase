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
import pl.thinkdata.b2bbase.company.dto.CompanyToEdit;
import pl.thinkdata.b2bbase.company.dto.CompanyToEditDto;
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
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.YOU_DONT_OWN_ANY_COMPANIES;
import static pl.thinkdata.b2bbase.company.comonent.SlugGenerator.toSlug;
import static pl.thinkdata.b2bbase.company.mapper.CompanyMapper.mapToCompanyResponse;
import static pl.thinkdata.b2bbase.company.mapper.CompanyMapper.mapToCompanyToEdit;

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

        String slug = checkIfSlugExistAndAddNumberToName(companyDto.getName());

        Company newCompany = companyRepository.save(CompanyMapper.mapToCompany(companyDto, slug));
        UserRole2Company userRole2Company = UserRole2Company.builder()
                .company(newCompany)
                .role(CompanyRole.ADMIN)
                .user(user)
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

    public CompanyToEdit getCompany(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        String username = tokenUtil.validTokenAndGetUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername())).get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));

        Company company = Optional.ofNullable(userRole2CompanyRepository.findByUser(user))
                .get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(YOU_DONT_OWN_ANY_COMPANIES)))
                .getCompany();
        return mapToCompanyToEdit(company);
    }

    public CompanyToEdit editCompany(CompanyToEditDto companyToEdit, HttpServletRequest request) {
        //TODO Add validation data
        Company company = companyRepository.findByNip(companyToEdit.getNip()).get();
        company.setName(companyToEdit.getName());
        company.setType(companyToEdit.getType());
        company.setLegalForm(companyToEdit.getLegalForm());
        company.setNip(companyToEdit.getNip());
        company.setRegon(companyToEdit.getRegon());
        company.setKrs(companyToEdit.getKrs());
        company.setEmail(companyToEdit.getEmail());
        company.setPhone(companyToEdit.getPhone());
        company.setWwwSite(companyToEdit.getWwwSite());
        company.setWwwStore(companyToEdit.getWwwStore());
        company.setEdiCooperation(companyToEdit.isEdiCooperation());
        company.setApiCooperation(companyToEdit.isApiCooperation());
        company.setProductFileCooperation(companyToEdit.isProductFileCooperation());

        return mapToCompanyToEdit(companyRepository.save(company));
    }

    private String checkIfSlugExistAndAddNumberToName(String name) {
        if (companyRepository.findBySlug(toSlug(name)).isPresent()) {
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
