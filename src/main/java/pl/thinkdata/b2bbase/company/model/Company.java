package pl.thinkdata.b2bbase.company.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    @Enumerated(EnumType.STRING)
    private ComapnyType type;
    @Enumerated(EnumType.STRING)
    private LegalForm legal_form;
    private String nip;
    private String regon;
    private String krs;
    private String email;
    private String phone;
    private String www_site;
    private String www_store;
    private boolean ediCooperation;
    private boolean apiCooperation;
    private boolean productFileCooperation;
    private String description;
    private String logo;
    @OneToMany(mappedBy = "company")
    private List<Branch> branches;

}