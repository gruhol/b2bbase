package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Branche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean headquarter;
    private String slug;
    private String post_code;
    private String city;
    private String street;
    private String house_number;
    private String office_number;
    private String email;
    private String phone;
    private String latitude;
    private String longitude;
}
