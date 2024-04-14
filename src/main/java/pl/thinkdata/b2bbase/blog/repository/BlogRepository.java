package pl.thinkdata.b2bbase.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.thinkdata.b2bbase.blog.model.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Optional<Blog> findBySlug(String slug);

    @Query(value = "SELECT b " +
                " FROM Blog b" +
                " WHERE" +
                "(:#{#category == null} = true OR b.category.id IN :category)" +
                " ORDER BY b.id DESC",
            countQuery = "SELECT count(*) FROM Blog b " +
                " WHERE" +
                "(:#{#category == null} = true OR b.category.id IN :category)" +
                " GROUP BY b.id")
    Page<Blog> findAllByCategory(@Param("category") List<Long> category, Pageable pageable);

    @Query(value = "SELECT b " +
            " FROM Blog b" +
            " WHERE" +
            " b.category.slug = :slug" +
            " ORDER BY b.id DESC",
            countQuery = "SELECT count(*) FROM Blog b " +
                    " WHERE" +
                    " b.category.slug = :slug" +
                    " GROUP BY b.id")
    Page<Blog> findAllByCategorySlug(@Param("slug") String slug, Pageable pageable);
}
