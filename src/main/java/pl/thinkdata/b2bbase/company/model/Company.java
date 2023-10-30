package pl.thinkdata.b2bbase.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    @Enumerated(EnumType.STRING)
    private CompanyTypeEnum type;
    @Enumerated(EnumType.STRING)
    private LegalFormEnum legalForm;
    private String nip;
    private String regon;
    private String krs;
    private String email;
    private String phone;
    private String wwwSite;
    private String wwwStore;
    private boolean ediCooperation;
    private boolean apiCooperation;
    private boolean productFileCooperation;
    private String description;
    private String logo;
    @OneToMany(mappedBy = "company")
    private List<Branch> branches;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "category_2_company",
            joinColumns = { @JoinColumn(name = "companyId") },
            inverseJoinColumns = { @JoinColumn(name = "categoryId") })
    @JsonIgnore
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getCompanies().add(this);
    }

    public void removeCategory(long categoryId) {
        Category category = this.categories.stream().filter(cat -> cat.getId() == categoryId).findFirst().orElse(null);
        if (category != null) {
            this.categories = this.categories.stream()
                    .filter(cat -> cat.getId() != categoryId)
                    .collect(Collectors.toSet());
            category.setCompanies(
                    category.getCompanies().stream()
                    .filter(company -> this.getId() != company.getId())
                    .collect(Collectors.toSet())
            );
        }
    }
}
