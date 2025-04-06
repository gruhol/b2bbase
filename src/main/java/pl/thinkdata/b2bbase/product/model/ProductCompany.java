package pl.thinkdata.b2bbase.product.model;

import jakarta.persistence.*;
import lombok.*;
import pl.thinkdata.b2bbase.company.model.Company;

import java.util.List;

@Entity
@Table(name = "product_company", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"company_id", "product_id"})
})
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "productCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;
}