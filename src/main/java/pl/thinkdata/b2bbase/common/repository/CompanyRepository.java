package pl.thinkdata.b2bbase.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.thinkdata.b2bbase.catalog.dto.CompanyInCatalog2;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByNip(String nip);

    Optional<Company> findByRegon(String regon);

    Optional<Company> findBySlug(String toSlug);

    @Query(value = "SELECT" +
            " c.name," +
            " c.slug," +
            " c.type," +
            " c.legalForm," +
            " c.nip," +
            " c.regon," +
            " c.krs," +
            " c.email," +
            " c.phone," +
            " c.wwwSite," +
            " c.wwwStore," +
            " c.ediCooperation," +
            " c.apiCooperation," +
            " c.productFileCooperation," +
            " c.logo" +
            " FROM Company c" +
            " LEFT OUTER JOIN Category2Company c2c ON c.id=c2c.companyId" +
            " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
            " LEFT OUTER JOIN Branch b ON b.companyId=c.id" +
            " WHERE" +
            " (COALESCE(:idCategories) IS NULL OR (cat.id IN (:idCategories)))" +
            " AND (COALESCE(:idBranches) IS NULL OR (b.id IN (:idBranches)))" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT OUTER JOIN Category2Company c2c ON c.id=c2c.companyId" +
                    " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
                    " LEFT OUTER JOIN Branch b ON b.companyId=c.id" +
                    " WHERE" +
                    " (COALESCE(:idCategories) IS NULL OR (cat.id IN (:idCategories)))" +
                    " AND (COALESCE(:idBranches) IS NULL OR (b.id IN (:idBranches)))")
    Page<CompanyInCatalog2> getAllCompanyToCatalog(
            @Param("idCategories") Long[] idCategories,
            @Param("idBranches") Long[] idSex,
            Pageable pageable);
}
