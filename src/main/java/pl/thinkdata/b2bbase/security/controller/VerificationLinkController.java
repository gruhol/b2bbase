package pl.thinkdata.b2bbase.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.security.dto.VerificationLinkResponse;
import pl.thinkdata.b2bbase.security.service.VerificationLinkService;

@RestController
@RequiredArgsConstructor
public class VerificationLinkController {

    private final VerificationLinkService service;

    @GetMapping("/verify/{token}")
    public VerificationLinkResponse checkVerificationLink(@PathVariable(value = "token", required = false) String token) {
        return service.checkVerificationLinkAndSendToken(token);
    }
}
