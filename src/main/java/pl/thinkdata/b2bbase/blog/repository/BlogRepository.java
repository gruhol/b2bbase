package pl.thinkdata.b2bbase.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.thinkdata.b2bbase.blog.model.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
