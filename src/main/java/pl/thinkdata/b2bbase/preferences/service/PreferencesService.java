package pl.thinkdata.b2bbase.preferences.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.thinkdata.b2bbase.common.error.InvalidRequestDataException;
import pl.thinkdata.b2bbase.common.util.MessageGenerator;
import pl.thinkdata.b2bbase.preferences.model.Preferences;
import pl.thinkdata.b2bbase.preferences.repository.PerferencesRepository;

import static pl.thinkdata.b2bbase.common.tool.ErrorDictionary.GET_PREFERENCE_ERROR;

@Service
@RequiredArgsConstructor
public class PreferencesService {

    private final PerferencesRepository perferencesRepository;
    private final MessageGenerator messageGenerator;

    public String getPerference(String key) {
        Preferences preference =  perferencesRepository.findByPreferenceKey(key)
                .orElseThrow(() ->  new InvalidRequestDataException(messageGenerator.get(GET_PREFERENCE_ERROR)));
        return preference.getPreferenceValue();
    }
}
