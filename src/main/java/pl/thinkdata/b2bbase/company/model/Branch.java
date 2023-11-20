package pl.thinkdata.b2bbase.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean headquarter;
    private String slug;
    @Enumerated(EnumType.STRING)
    private VoivodeshipEnum voivodeship;
    private String post_code;
    private String city;
    private String street;
    private String house_number;
    private String office_number;
    private String email;
    private String phone;
    private String latitude;
    private String longitude;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    @Column(name="company_id", insertable=false, updatable=false)
    private Long companyId;
}
