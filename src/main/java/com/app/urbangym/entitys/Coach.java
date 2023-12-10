package com.app.urbangym.entitys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COACHS")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_coach;

    @Size(min = 8, max = 8, message = "El DNI debe contener exactamente 8 dígitos")
    @NotNull(message = "El DNI es obligatorio")
    @Column(name = "DNI_COACH", unique = true)
    private String dni;

    @NotBlank(message = "El campo del nombre no puede estar vacío")
    @Column(name = "NAME")
    private String name;

    @NotBlank(message = "El campo de apellido no puede estar vacío")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotBlank(message = "El campo de email no puede estar vacío")
    @Column(name = "EMAIL", unique = true)
    private String email;

    @NotBlank(message = "El campo de la contraseña no puede estar vacío")
    @Column(name = "PASSWORD")
    private String password;

    @Lob
    @Column(name = "COACH_PHOTO")
    private byte[] photo;

    @OneToMany(mappedBy = "coach", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToOne
    private Administrator admin;

    public long getId_coach() {
        return this.id_coach;
    }

    public void setId_coach(long id_coach) {
        this.id_coach = id_coach;
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

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Administrator getAdmin() {
        return this.admin;
    }

    public void setAdmin(Administrator admin) {
        this.admin = admin;
    }

}
