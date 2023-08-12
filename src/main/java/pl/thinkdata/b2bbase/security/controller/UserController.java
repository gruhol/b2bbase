package pl.thinkdata.b2bbase.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.security.dto.UserDto;
import pl.thinkdata.b2bbase.security.dto.UserEditData;
import pl.thinkdata.b2bbase.security.dto.RegisterCredentials;
import pl.thinkdata.b2bbase.security.model.Token;
import pl.thinkdata.b2bbase.security.service.UserService;

import java.util.List;

@RestController
public class UserController {

    private static final String TOKEN_HEADER = "Authorization";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginCredentials loginCredentials) {
        return userService.authenticate(loginCredentials.username, loginCredentials.password);
    }

    @GetMapping("/user/role")
    public List<String> getRole(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        return userService.getRole(token);
    }

    @GetMapping("/user/get")
    public UserEditData getUserData(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        return userService.getUserData(token);
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody @Valid RegisterCredentials registerCredentials) {
        return userService.register(registerCredentials);
    }

    @PostMapping("/user/edit")
    public UserEditData editUserData(@RequestBody @Valid UserDto userDto) {
        return userService.editUserData(userDto);
    }

    @Getter
    private static class  LoginCredentials {
        private String username;
        private String password;
    }
}