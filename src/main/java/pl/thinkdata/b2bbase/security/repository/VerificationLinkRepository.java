package pl.thinkdata.b2bbase.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.security.model.VerificationLink;

public interface VerificationLinkRepository extends JpaRepository<VerificationLink, Long> {
}
