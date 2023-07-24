package pl.thinkdata.b2bbase.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.security.service.VerificationLinkService;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class VerificationLinkController {

    private final VerificationLinkService service;

    @GetMapping("/verify/{token}")
    public boolean checkVerificationLink(@PathVariable(value = "token", required = false) String token) {
        return service.checkVerificationLink(token);
    }
}
