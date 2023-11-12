package pl.thinkdata.b2bbase.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByNip(String nip);

    Optional<Company> findByRegon(String regon);

    Optional<Company> findBySlug(String toSlug);
}
