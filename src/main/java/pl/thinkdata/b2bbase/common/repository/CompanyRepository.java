package pl.thinkdata.b2bbase.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    Optional<Company> findByNip(String nip);

    Optional<Company> findByRegon(String regon);

    Optional<Company> findBySlug(String toSlug);

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
            " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
            " WHERE" +
            " (:idCategories IS NULL OR (cat.id IN (:idCategories)))" +
            " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
            " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
            " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT OUTER JOIN Category2Company c2c ON c.id=c2c.companyId" +
                    " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
                    " WHERE" +
                    " (:idCategories IS NULL OR (cat.id IN (:idCategories)))" +
                    " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
                    " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
                    " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)")
    Page<Company> getAllCompanyToCatalog(
            @Param("idCategories") List<Long> idCategories,
            @Param("isEdiCooperation") Boolean isEdiCooperation,
            @Param("isApiCooperation") Boolean isApiCooperation,
            @Param("isProductFileCooperation") Boolean isProductFileCooperation,
            Pageable pageable);
}
