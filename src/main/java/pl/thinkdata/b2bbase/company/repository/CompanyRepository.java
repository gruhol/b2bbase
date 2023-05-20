package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByNip(String nip);

    Optional<Company> findByNip(String nip);
}
