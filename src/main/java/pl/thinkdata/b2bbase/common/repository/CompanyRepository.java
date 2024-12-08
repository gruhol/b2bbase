package pl.thinkdata.b2bbase.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.thinkdata.b2bbase.company.model.Company;
import pl.thinkdata.b2bbase.company.model.enums.VoivodeshipEnum;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    Optional<Company> findByNip(String nip);

    Optional<Company> findByRegon(String regon);

    Optional<Company> findBySlugAndActive(String toSlug, boolean isActive);

    Optional<Company> findBySlug(String slug);

    List<Company> findAllByOrderByCreatedDesc(Pageable pageable);

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " WHERE" +
            " (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
            " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
            " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " WHERE" +
                    " (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
                    " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
                    " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)")
    Page<Company> getAllCompanyToCatalog(
            @Param("isEdiCooperation") Boolean isEdiCooperation,
            @Param("isApiCooperation") Boolean isApiCooperation,
            @Param("isProductFileCooperation") Boolean isProductFileCooperation,
            Pageable pageable);

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
            " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
            " WHERE" +
            " (cat.id IN :idCategories)" +
            " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
            " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
            " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT OUTER JOIN Category2Company c2c ON c.id=c2c.companyId" +
                    " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
                    " WHERE" +
                    " (cat.id IN :idCategories)" +
                    " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
                    " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
                    " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)")
    Page<Company> getAllCompanyByCategoryToCatalog(
            @Param("idCategories") List<Long> idCategories,
            @Param("isEdiCooperation") Boolean isEdiCooperation,
            @Param("isApiCooperation") Boolean isApiCooperation,
            @Param("isProductFileCooperation") Boolean isProductFileCooperation,
            Pageable pageable);

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " LEFT JOIN Branch b ON c.id = b.companyId" +
            " WHERE" +
            "(b.voivodeship IN :voivodeshipes)" +
            " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
            " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
            " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT JOIN Branch b ON c.id = b.companyId" +
                    " WHERE" +
                    " (b.voivodeship IN :voivodeshipes)" +
                    " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
                    " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
                    " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)")
    Page<Company> getAllCompanyByVoivodeshipToCatalog(
            @Param("voivodeshipes") List<VoivodeshipEnum> voivodeshipes,
            @Param("isEdiCooperation") Boolean isEdiCooperation,
            @Param("isApiCooperation") Boolean isApiCooperation,
            @Param("isProductFileCooperation") Boolean isProductFileCooperation,
            Pageable pageable);

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
            " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
            " LEFT JOIN Branch b ON c.id = b.companyId" +
            " WHERE" +
            "(:#{#voivodeshipes == null} = true OR b.voivodeship IN :voivodeshipes)" +
            " AND (:#{#idCategories == null} = true OR cat.id IN :idCategories)" +
            " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
            " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
            " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
            " AND (c.active = true)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
                    " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
                    " LEFT JOIN Branch b ON c.id = b.companyId" +
                    " WHERE" +
                    "(:#{#voivodeshipes == null} = true OR b.voivodeship IN :voivodeshipes)" +
                    " AND (:#{#idCategories == null} = true OR cat.id IN :idCategories)" +
                    " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
                    " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
                    " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
                    " AND (c.active = true)" +
                    " GROUP BY c.id")
    Page<Company> getAllCompanyByVoivodeshipAndCategoryToCatalog(
            @Param("idCategories") List<Long> idCategories,
            @Param("voivodeshipes") List<VoivodeshipEnum> voivodeshipes,
            @Param("isEdiCooperation") Boolean isEdiCooperation,
            @Param("isApiCooperation") Boolean isApiCooperation,
            @Param("isProductFileCooperation") Boolean isProductFileCooperation,
            Pageable pageable);

    @Query(value = "SELECT c " +
            " FROM Company c" +
            " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
            " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
            " LEFT JOIN Branch b ON c.id = b.companyId" +
            " WHERE" +
            "(:#{#voivodeshipes == null} = true OR b.voivodeship IN :voivodeshipes)" +
            " AND (:#{#idCategories == null} = true OR cat.id IN :idCategories)" +
            " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
            " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
            " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
            " AND (c.active = true)" +
            " AND (LOWER(c.name) LIKE :keyword " +
            " OR c.nip LIKE :keyword " +
            " OR c.regon LIKE :keyword " +
            " OR LOWER(cat.name) LIKE :keyword)" +
            " GROUP BY c.id" +
            " ORDER BY c.id DESC",
            countQuery = "SELECT count(*) FROM Company c " +
                    " LEFT OUTER JOIN Category2Company c2c ON c.id = c2c.companyId" +
                    " LEFT OUTER JOIN Category cat ON c2c.categoryId = cat.id" +
                    " LEFT JOIN Branch b ON c.id = b.companyId" +
                    " WHERE" +
                    "(:#{#voivodeshipes == null} = true OR b.voivodeship IN :voivodeshipes)" +
                    " AND (:#{#idCategories == null} = true OR cat.id IN :idCategories)" +
                    " AND (:isEdiCooperation IS NULL OR c.ediCooperation = :isEdiCooperation)" +
                    " AND (:isApiCooperation IS NULL OR c.apiCooperation = :isApiCooperation)" +
                    " AND (:isProductFileCooperation IS NULL OR c.productFileCooperation = :isProductFileCooperation)" +
                    " AND (c.active = true)" +
                    " AND (LOWER(c.name) LIKE :keyword " +
                    " OR c.nip LIKE :keyword " +
                    " OR c.regon LIKE :keyword " +
                    " OR LOWER(cat.name) LIKE :keyword)" +
                    " GROUP BY c.id")
    Page<Company> searchByKeyword(@Param("keyword") String keyword,
                                  @Param("idCategories") List<Long> idCategories,
                                  @Param("voivodeshipes") List<VoivodeshipEnum> voivodeshipes,
                                  @Param("isEdiCooperation") Boolean isEdiCooperation,
                                  @Param("isApiCooperation") Boolean isApiCooperation,
                                  @Param("isProductFileCooperation") Boolean isProductFileCooperation,
                                  Pageable pageable);
}
