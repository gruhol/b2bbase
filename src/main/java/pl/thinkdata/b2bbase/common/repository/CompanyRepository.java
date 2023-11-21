package pl.thinkdata.b2bbase.common.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog2;
import pl.thinkdata.b2bbase.company.model.Category;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    Optional<Company> findByNip(String nip);

    Optional<Company> findByRegon(String regon);

    Optional<Company> findBySlug(String toSlug);

    Page<Company> findAllByCategoriesIn(List<Category> categories, Pageable pageable);

    default Page<Company> findAllByCategoriesInAndCriteria(
            List<Category> categories,
            Boolean isEdiCooperation,
            Boolean isApiCooperation,
            Boolean isProductFileCooperation,
            Pageable pageable) {

        return findAll(
                (Specification<Company>) (root, query, criteriaBuilder) -> {
                    // Tworzenie warunków zapytania na podstawie wartości parametrów
                    query.distinct(true);
                    query.orderBy(criteriaBuilder.desc(root.get("name")));

                    Predicate predicate = criteriaBuilder.conjunction();

                    // Warunek dla kategorii
                    if (categories != null && !categories.isEmpty()) {
                        predicate = criteriaBuilder.and(predicate, root.get("categories").in(categories));
                    }

                    // Warunek dla EdiCooperation
                    if (isEdiCooperation != null) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ediCooperation"), isEdiCooperation));
                    }

                    // Warunek dla ApiCooperation
                    if (isApiCooperation != null) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("apiCooperation"), isApiCooperation));
                    }

                    // Warunek dla ProductFileCooperation
                    if (isProductFileCooperation != null) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("productFileCooperation"), isProductFileCooperation));
                    }

                    // Dodanie warunku, aby jednocześnie isEdiCooperation, isApiCooperation, isProductFileCooperation spełniały warunki
                    if (Boolean.TRUE.equals(isEdiCooperation) && Boolean.TRUE.equals(isApiCooperation) && Boolean.TRUE.equals(isProductFileCooperation)) {
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.isTrue(root.get("ediCooperation")),
                                criteriaBuilder.isTrue(root.get("apiCooperation")),
                                criteriaBuilder.isTrue(root.get("productFileCooperation"))
                        );
                    } else if (Boolean.FALSE.equals(isEdiCooperation) && Boolean.FALSE.equals(isApiCooperation) && Boolean.FALSE.equals(isProductFileCooperation)) {
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.isFalse(root.get("ediCooperation")),
                                criteriaBuilder.isFalse(root.get("apiCooperation")),
                                criteriaBuilder.isFalse(root.get("productFileCooperation"))
                        );
                    } else if (isEdiCooperation == null && isApiCooperation == null && isProductFileCooperation == null) {
                        predicate = criteriaBuilder.and(predicate,
                                criteriaBuilder.isNull(root.get("ediCooperation")),
                                criteriaBuilder.isNull(root.get("apiCooperation")),
                                criteriaBuilder.isNull(root.get("productFileCooperation"))
                        );
                    }

                    return predicate;
                },
                pageable
        );
    }

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
            " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
            " WHERE" +
            " (:idCategories IS NULL OR (cat.id IN (:idCategories)))" +
//            " cat.id IN (:idCategories)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT OUTER JOIN Category2Company c2c ON c.id=c2c.companyId" +
                    " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
                    " WHERE" +
//                    " cat.id IN (:idCategories)")
                    " (:idCategories IS NULL OR (cat.id IN (:idCategories)))")
    Page<Company> getAllCompanyToCatalog(
            @Param("idCategories") List<Long> idCategories,
            Pageable pageable);
}
