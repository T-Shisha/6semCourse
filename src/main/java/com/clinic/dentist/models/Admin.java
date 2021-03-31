package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String login;
    @Transient
    private String passwordConfirm;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_role", joinColumns = @JoinColumn(name = "admin_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
