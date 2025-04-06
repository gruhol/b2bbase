package pl.thinkdata.b2bbase.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thinkdata.b2bbase.product.model.ProductCompany;

import java.util.List;

@Repository
public interface ProductCompanyRepository extends JpaRepository<ProductCompany, Long> {
    List<ProductCompany> findByProduct_Ean(String ean);
}
