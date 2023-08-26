package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.UserRole2Company;

public interface UserRole2CompanyRepository extends JpaRepository<UserRole2Company, Long> {
}
