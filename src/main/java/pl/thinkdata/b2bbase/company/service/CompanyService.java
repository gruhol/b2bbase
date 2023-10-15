package pl.thinkdata.b2bbase.company.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.*;
import pl.thinkdata.b2bbase.company.mapper.CompanyMapper;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.CompanyRoleEnum;
import pl.thinkdata.b2bbase.company.model.UserRole2Company;
import pl.thinkdata.b2bbase.company.repository.CompanyRepository;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.company.validator.EditCompanyValidator;
import pl.thinkdata.b2bbase.company.validator.RegistrationValidator;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.*;
import static pl.thinkdata.b2bbase.company.component.SlugGenerator.toSlug;
import static pl.thinkdata.b2bbase.company.mapper.CompanyMapper.*;
import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;

@Service
@RequiredArgsConstructor
public class CompanyService {

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

        if(userRole2CompanyRepository.findByUser(user).isPresent()) {
            throw new InvalidRequestDataException(messageGenerator.get(YOU_CAN_ADD_ONLY_ONE_COMPANY));
        }

        String slug = checkIfSlugExistAndAddNumberToName(companyDto.getName());

        Company newCompany = companyRepository.save(CompanyMapper.mapToCompany(companyDto, slug));
        UserRole2Company userRole2Company = UserRole2Company.builder()
                .company(newCompany)
                .role(CompanyRoleEnum.ADMIN)
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

    public CompanyToEdit getCompanyToEdit(HttpServletRequest request) {
        Company company = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));

        return mapToCompanyToEdit(company);
    }

    public Company getCompany(HttpServletRequest request) {
        return getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
    }

    public CompanyToEdit editCompany(CompanyToEditDto companyToEdit, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        EditCompanyValidator editCompanyValidator= new EditCompanyValidator(companyToEdit, companyInBase, this);
        editCompanyValidator.valid();

        companyInBase.setName(companyToEdit.getName());
        companyInBase.setType(companyToEdit.getType());
        companyInBase.setLegalForm(companyToEdit.getLegalForm());
        companyInBase.setNip(companyToEdit.getNip());
        companyInBase.setRegon(companyToEdit.getRegon());
        companyInBase.setKrs(companyToEdit.getKrs());
        companyInBase.setEmail(companyToEdit.getEmail());
        companyInBase.setPhone(companyToEdit.getPhone());
        companyInBase.setWwwSite(companyToEdit.getWwwSite());
        companyInBase.setWwwStore(companyToEdit.getWwwStore());
        companyInBase.setEdiCooperation(companyToEdit.isEdiCooperation());
        companyInBase.setApiCooperation(companyToEdit.isApiCooperation());
        companyInBase.setProductFileCooperation(companyToEdit.isProductFileCooperation());

        return mapToCompanyToEdit(companyRepository.save(companyInBase));
    }

    public CompanyToEdit editAdditionalDataCompany(AdditionalDataToEdit additionalDataToEdit, HttpServletRequest request) {
        Company companyInBase = getUserCompanyByToken(request.getHeader(TOKEN_HEADER));
        companyInBase.setDescription(additionalDataToEdit.getDescription());

        return mapToCompanyToEdit(companyRepository.save(companyInBase));
    }

    private Company getUserCompanyByToken(String token) {
        String username = tokenUtil.validTokenAndGetUsername(token);
        return tokenUtil.getCompanyByUsernameFormDataBase(username);
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
