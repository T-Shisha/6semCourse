package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean active;
    @Transient
    private String passwordConfirm;
    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Appointment> orders;
    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Review> reviews;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "patient_role", joinColumns = @JoinColumn(name = "patient_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
