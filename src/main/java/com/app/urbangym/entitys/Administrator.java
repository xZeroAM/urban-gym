package com.app.urbangym.entitys;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ADMINISTRATORS", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "DNI_ADMINISTRATOR", "EMAIL" }) })
public class Administrator {

    @Id
    @NotNull(message = "El dni no puede ser nulo")
    @Column(name = "DNI_ADMINISTRATOR")
    private Long dni;

    @NotBlank(message = "El nombre no puede ser nulo")
    @Column(name = "NAME")
    private String name;

    @NotBlank(message = "El apellido no puede ser nulo")
    @Column(name = "LAST_NAME")
    private String lastName;

    @Email
    @NotBlank(message = "El email no puede ser nulo")
    @Column(name = "EMAIL")
    private String email;

    @NotBlank(message = "La contraseña no puede ser nula")
    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coach> coachs;

    public Long getDni() {
        return this.dni;
    }

    public void setDni(Long dni) {
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

    public List<Coach> getCoachs() {
        return this.coachs;
    }

    public void setCoachs(List<Coach> coachs) {
        this.coachs = coachs;
    }

}
