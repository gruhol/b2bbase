package pl.thinkdata.b2bbase.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.company.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findBySlug(String slug);

    List<Category> findByParent(Category categories);
}
