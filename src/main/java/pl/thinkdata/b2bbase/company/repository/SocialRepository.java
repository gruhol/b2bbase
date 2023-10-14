package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Social;

import java.util.List;

public interface SocialRepository extends JpaRepository<Social, Long> {
    List<Social> findAllByCompanyId(Long companyId);
}
