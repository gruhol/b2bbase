package pl.thinkdata.b2bbase.company.validator.predicate;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.common.util.TokenUtil;
import pl.thinkdata.b2bbase.company.dto.SubscriptionCompanyWithRequestDto;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.repository.UserRole2CompanyRepository;
import pl.thinkdata.b2bbase.company.service.CompanyService;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Optional;
import java.util.function.Predicate;

import static pl.thinkdata.b2bbase.common.tool.LoginDictionary.TOKEN_HEADER;

@Component
@RequiredArgsConstructor
public class UserIsNotAdminCompany<T extends SubscriptionCompanyWithRequestDto> implements Predicate<SubscriptionCompanyWithRequestDto> {

    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final MessageGenerator messageGenerator;
    private final UserRole2CompanyRepository userRole2CompanyRepository;
    private final CompanyService companyService;

    @Override
    public boolean test(SubscriptionCompanyWithRequestDto dto) {
        String token = dto.getRequest().getHeader(TOKEN_HEADER);

        String username = tokenUtil.validTokenAndGetUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Company> company = companyService.findById(dto.getCompanyId());
        if (company.isEmpty() || user.isEmpty()) return true;
        return userRole2CompanyRepository.findByUserAndCompany(user.get(), company.get()).isEmpty();
    }
}
