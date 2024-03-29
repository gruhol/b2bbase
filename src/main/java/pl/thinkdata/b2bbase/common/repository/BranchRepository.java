package pl.thinkdata.b2bbase.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog;
import pl.thinkdata.b2bbase.company.model.Branch;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAllByCompany(Company company);

    Optional<Branch> findBySlug(String toSlug);

    Optional<Branch> findByCompanyIdAndHeadquarter(Long id, boolean b);
}
