package pl.thinkdata.b2bbase.company.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.thinkdata.b2bbase.company.dto.EditSocialDto;
import pl.thinkdata.b2bbase.company.dto.SocialDto;
import pl.thinkdata.b2bbase.company.model.Social;
import pl.thinkdata.b2bbase.company.service.SocialService;

import java.util.List;

@RestController
@RequestMapping("/social")
@RequiredArgsConstructor
public class SocialController {

    private final SocialService socialService;

    @PostMapping
    public Social addSocial(@RequestBody @Valid SocialDto socialDto, HttpServletRequest request) {
        return socialService.addSocial(socialDto, request);
    }

    @GetMapping
    public List<Social> getSocials(HttpServletRequest  request) {
        return socialService.getSocials(request);
    }

    @PutMapping
    public Social editSocial(@RequestBody @Valid EditSocialDto editSocialDto, HttpServletRequest request) {
        return socialService.editSocial(editSocialDto, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSocial(@PathVariable(value = "id", required = true) Long id, HttpServletRequest request) {
        socialService.deleteSocial(id,request);
    }
}
