package pl.thinkdata.b2bbase.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thinkdata.b2bbase.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
