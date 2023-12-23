package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.*;
import lombok.*;
import pl.thinkdata.b2bbase.company.model.enums.SocialTypeEnum;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SocialTypeEnum type;
    private String url;
    private Long companyId;
}
