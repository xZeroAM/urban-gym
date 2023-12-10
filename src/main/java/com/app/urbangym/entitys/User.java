package com.app.urbangym.entitys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_user;

    @Size(min = 8, max = 8, message = "El DNI debe contener exactamente 8 dígitos")
    @NotNull(message = "El DNI es obligatorio")
    @Column(name = "DNI_USER", unique = true)
    private String dni;

    @NotBlank(message = "El campo del nombre no puede estar vacío")
    @Column(name = "NAME")
    private String name;

    @NotBlank(message = "El campo de apellido no puede estar vacío")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotBlank(message = "El campo del celular no puede estar vacío")
    @Column(name = "CELL_PHONE")
    private String cellPhone;

    @NotBlank(message = "El campo de email no puede estar vacío")
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotBlank(message = "El campo de la contraseña no puede estar vacío")
    @Column(name = "PASSWORD")
    private String password;

    @Lob
    @Column(name = "USER_PHOTO")
    private byte[] photo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Routine> routines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COACH_DNI")
    private Coach coach;

    // @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    // private List<Plan> plans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    public Long getId_user() {
        return this.id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public List<Routine> getRoutines() {
        return this.routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    public Coach getCoach() {
        return this.coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<Membership> getMemberships() {
        return this.memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

}
