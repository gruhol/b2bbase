package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.*;
import lombok.*;

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
    private long user_id;
    @Enumerated(EnumType.STRING)
    private CompanyRole role;
    private long company_id;
}
