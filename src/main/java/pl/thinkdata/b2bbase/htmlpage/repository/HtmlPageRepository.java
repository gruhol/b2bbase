package pl.thinkdata.b2bbase.htmlpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.htmlpage.model.HtmlPage;

import java.util.Optional;

public interface HtmlPageRepository extends JpaRepository<HtmlPage, Long> {

    Optional<HtmlPage> findBySlug(String slug);
}
