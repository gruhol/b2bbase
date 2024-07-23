package pl.thinkdata.b2bbase.preferences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.thinkdata.b2bbase.preferences.service.PreferencesService;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
public class PreferencesController {

    private final PreferencesService preferencesService;
    @GetMapping("/get/{key}")
    public String getPreference(@PathVariable String key) {
        return preferencesService.getPerference(key);
    }
}
