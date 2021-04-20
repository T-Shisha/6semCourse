package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;
    @Transient
    private String passwordConfirm;
    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Appointment> orders;
    /* @SuppressWarnings("JpaAttributeTypeInspection")
     @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient", cascade = {CascadeType.ALL}, orphanRemoval = true)
     private Set<Review> reviews;*/
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "patient_role", joinColumns = @JoinColumn(name = "patient_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

   /* public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }*/

    public void setOrders(Set<Appointment> orders) {
        this.orders = orders;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }


}
