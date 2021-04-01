package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String username;
    private String h;
    private boolean active;
    @Transient
    private String passwordConfirm;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "admin_role", joinColumns = @JoinColumn(name = "admin_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
