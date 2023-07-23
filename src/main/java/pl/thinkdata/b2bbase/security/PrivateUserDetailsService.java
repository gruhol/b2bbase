package pl.thinkdata.b2bbase.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.thinkdata.b2bbase.common.error.AuthorizationException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.security.model.PrivateUserDetails;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.AUTHORIZATION_FAILED;

@Service
@RequiredArgsConstructor
public class PrivateUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageGenerator messageGenerator;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, AuthorizationException {
        User user = userRepository.findById(Long.parseLong(username))
                .orElseThrow(() -> new AuthorizationException(messageGenerator.get(AUTHORIZATION_FAILED)));
        PrivateUserDetails privateUserDetails = new PrivateUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities().stream()
                        .map(userRole -> (GrantedAuthority) () -> userRole.name())
                        .toList()
        );
        privateUserDetails.setId(user.getId());
        return privateUserDetails;
    }
}
