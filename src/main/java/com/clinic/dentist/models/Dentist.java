package com.clinic.dentist.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Dentist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;

    @OneToMany(mappedBy = "dentist", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Appointment> orders;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "dentist_maintenance",
            joinColumns = @JoinColumn(name = "Dentist"),
            inverseJoinColumns = @JoinColumn(name = "Maintenance")
    )
    private Set<Maintenance> maintenances;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Set<Maintenance> getMaintenances() {
        return maintenances;
    }

    public List<Appointment> getOrders() {
        return orders;
    }

    public Dentist() {
    }

    public Long getId() {
        return id;
    }

    public void deleteService(Maintenance maintenance) {
        this.maintenances.remove(maintenance);
        maintenance.getDentists().remove(this);
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setMaintenances(Set<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
