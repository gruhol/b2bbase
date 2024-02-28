package pl.thinkdata.b2bbase.staticpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.thinkdata.b2bbase.staticpage.model.StaticPage;

import java.util.Optional;

@Repository
public interface StaticPageRepository extends JpaRepository<StaticPage, Long> {

    Optional<StaticPage> getBySlug(String slug);
}
