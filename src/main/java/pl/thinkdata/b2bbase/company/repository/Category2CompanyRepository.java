package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Category2Company;

import java.util.List;

public interface Category2CompanyRepository extends JpaRepository<Category2Company, Long> {

    List<Category2Company> findAllByCompanyId(Long companyId);
}
