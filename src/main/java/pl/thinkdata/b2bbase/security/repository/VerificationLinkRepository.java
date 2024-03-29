package pl.thinkdata.b2bbase.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.security.model.User;
import pl.thinkdata.b2bbase.security.model.VerificationLink;

import java.util.Collection;
import java.util.Optional;

public interface VerificationLinkRepository extends JpaRepository<VerificationLink, Long> {
    Optional<VerificationLink> findByToken(String token);

    Collection<VerificationLink> findByUser(User user);
}
