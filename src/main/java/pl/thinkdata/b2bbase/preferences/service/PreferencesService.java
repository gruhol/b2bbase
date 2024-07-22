package pl.thinkdata.b2bbase.preferences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.preferences.repository.PerferencesRepository;

@Service
@RequiredArgsConstructor
public class PreferencesService {

    private final PerferencesRepository perferencesRepository;

    public String getPerference(String key) {
        return perferencesRepository.findByPreferenceKey(key).orElse(null);
    }
}
