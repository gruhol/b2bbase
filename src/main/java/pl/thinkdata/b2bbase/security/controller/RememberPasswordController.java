package pl.thinkdata.b2bbase.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.security.service.RememberPasswordService;

@RestController
@RequiredArgsConstructor
public class RememberPasswordController {

    private final RememberPasswordService rememberPasswordService;

    @PostMapping("/remember-password")
    public boolean checkUserNameAndSendLinkWithTokenToRememberPassword(@RequestBody String email) {
        return rememberPasswordService.checkUserNameAndSendLinkWithTokenToRememberPassword(email);
    }

    @GetMapping("/send-password/{token}")
    public boolean checkTokenAndSendNewPassword(@PathVariable(value = "token", required = false) String token) {
        return rememberPasswordService.checkTokenAndSendNewPassword(token);
    }
}
