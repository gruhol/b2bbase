package pl.thinkdata.b2bbase.preferences.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.preferences.model.Preferences;

import java.util.Optional;

public interface PerferencesRepository extends JpaRepository<Preferences, Long> {

    Optional<String> findByPreferenceKey(String key);
}
