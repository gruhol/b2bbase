package pl.thinkdata.b2bbase.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import pl.thinkdata.b2bbase.user.validator.PhoneValidation;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String sureName;
    @NotBlank
    private String email;
    @PhoneValidation
    private String phone;
}
