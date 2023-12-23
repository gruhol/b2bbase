package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thinkdata.b2bbase.company.model.PackageOrder;

import java.util.List;

@Repository
public interface PackageOrderRepository extends JpaRepository<PackageOrder, Long> {
    List<PackageOrder> findAllByCompanyId(Long id);
}
