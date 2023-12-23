package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.*;
import lombok.*;
import pl.thinkdata.b2bbase.company.model.enums.CompanyRoleEnum;
import pl.thinkdata.b2bbase.security.model.User;

@Entity
@Table(name = "user_role_2_company")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRole2Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private CompanyRoleEnum role;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
