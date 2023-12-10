package com.app.urbangym.entitys;

// import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PLANS")
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_PLAN")
    private long id;

    @NotBlank(message = "El campo del titulo no puede estar vacío")
    @Column(name = "TITLE")
    private String title;

    @NotBlank(message = "El campo de la descripción no puede estar vacío")
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull(message = "El campo del precio no puede estar vacío")
    @Column(name = "PRICE")
    private double price;

    @NotNull(message = "El campo de la duración no puede estar vacío")
    @Column(name = "DURATION")
    private double duration;

    @Column(name = "PLAN_PHOTO")
    private byte[] photo;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    
    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public List<Membership> getMemberships() {
        return this.memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }


}
