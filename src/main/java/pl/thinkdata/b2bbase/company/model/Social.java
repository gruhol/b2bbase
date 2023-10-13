package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SocialType type;
    private String url;
    private Long company_id;
}
