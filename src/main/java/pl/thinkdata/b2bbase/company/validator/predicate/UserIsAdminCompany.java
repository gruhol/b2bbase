package pl.thinkdata.b2bbase.company.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyDto;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Optional;
import java.util.function.Predicate;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.USER_FROM_GIVEN_TOKEN_NOT_FOUND;
import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.YOU_CAN_ADD_ONLY_ONE_COMPANY;
import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class UserIsAdminCompany<T extends SubscriptionCompanyDto> implements Predicate<SubscriptionCompanyDto> {

    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final MessageGenerator messageGenerator;
    private final UserRole2CompanyRepository userRole2CompanyRepository;
    private final CompanyService companyService;

    @Override
    public boolean test(SubscriptionCompanyDto dto) {
        String token = dto.getRequest().getHeader(TOKEN_HEADER);

        String username = tokenUtil.validTokenAndGetUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = Optional.ofNullable(userRepository.findByUsername(userDetails.getUsername())).get()
                .orElseThrow(() -> new InvalidRequestDataException(messageGenerator.get(USER_FROM_GIVEN_TOKEN_NOT_FOUND)));
        Optional<Company> company = companyService.findById(dto.getCompanyId());
        if (company.isEmpty()) return false;
        return userRole2CompanyRepository.findByUserAndCompany(user, company.get()).isPresent();
    }
}
