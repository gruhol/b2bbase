package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.UserRole2Company;
import pl.thinkdata.b2bbase.security.model.User;

import java.util.Optional;

public interface UserRole2CompanyRepository extends JpaRepository<UserRole2Company, Long> {
    Optional<UserRole2Company> findByUser(User user);

    Optional<UserRole2Company> findByUserAndCompany(User user, Company company);
}
