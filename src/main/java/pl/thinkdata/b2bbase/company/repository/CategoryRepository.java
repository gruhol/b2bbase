package pl.thinkdata.b2bbase.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
