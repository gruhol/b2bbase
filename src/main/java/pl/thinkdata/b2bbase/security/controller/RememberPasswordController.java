package pl.thinkdata.b2bbase.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.repository.UserRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RememberPasswordController {

    private final UserRepository userRepository;

    @GetMapping("/remember-password")
    public boolean checkUserNameAndSendLinkWithToken(@RequestBody String email) {
        Optional<User> user = userRepository.findByUsername(email);

        return false;
    }
}
